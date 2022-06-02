package com.don.usersservice.service.impl;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.exception.ConflictException;
import com.don.usersservice.exception.EntityNotFoundException;
import com.don.usersservice.exception.GenericBadRequestException;
import com.don.usersservice.mapper.UserMapper;
import com.don.usersservice.model.Role;
import com.don.usersservice.model.User;
import com.don.usersservice.model.enums.ERole;
import com.don.usersservice.repository.RoleRepository;
import com.don.usersservice.repository.UserRepository;
import com.don.usersservice.security.RequestPrincipalContext;
import com.don.usersservice.service.UserDeleteService;
import com.don.usersservice.service.UserEventPrepare;
import com.don.usersservice.service.UserService;
import com.don.usersservice.service.UserTransactionalHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static com.don.usersservice.model.enums.ERole.values;

/**
 * @author Donald Veizi
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserTransactionalHelper transactionalHelper;
    private final UserDeleteService userDeleteService;
    private final UserEventPrepare userEventPrepare;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RequestPrincipalContext requestPrincipalContext;

    @Override
    public UserDTO register(UserRegisterRequest registerRequest) {
        verifyEmailNotInUse(registerRequest.getEmail());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User userToSave = userMapper.registerRequestToUser(registerRequest);
        setRolesToUserToBeSaved(userToSave, registerRequest);
        User user = userRepository.save(userToSave);
        // prepares the message in @Async way (in a parallel thread), and sends it to event producer.
        // This way the current thread that is executing register()
        // does not have to wait for the message preparation and it being sent by the producer
        userEventPrepare.produceUserCreatedEvent(user);
        return userMapper.userToUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                            log.error(String.format("Could not find user with email: %s", email));
                            throw new EntityNotFoundException("No user found with the provided email");
                        }
                );
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id: {}, was not found!", userId);
                    throw new EntityNotFoundException(
                            String.format("User with id: %s, was not found.", userId));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserProfile(final Long userId) {
        // TODO:
        return null;
    }

    @Override
    // TODO: check if in total 1 SELECT & 1 DELETE is made if we do not use @Transactional. I worry that without @Transactional,
    // since objects will not be in managed state, after the SELECT, the delete() will cause another SELECT then DELETE
    //@Transactional
    public void deleteAccount(final Long userId) {
        final long loggedUserId = requestPrincipalContext.getUserId();
        final String loggedUserEmail = requestPrincipalContext.getUserEmail();

        final User dbUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("User with id: %s, was not found", userId));
                });

        if (!loggedUserEmail.equals(dbUser.getEmail())
                || !dbUser.getId().equals(loggedUserId)) {
            log.error("The user account that should be deleted is not the account of the logged in user");
            throw new GenericBadRequestException("The account that is being attempted to be deleted, is not the user's account.");
        }

        userDeleteService.deleteUserAndRelated(dbUser);
        userEventPrepare.produceUserDeletedEvent(dbUser.getId());
    }

    private void verifyEmailNotInUse(final String email) {
        if (email != null && userRepository.existsByEmail(email)) {
            log.error("Account with email {}, already exists. Can't register this user", email);
            // better to use a checked exception (make it extends Exception and not Runtimeexception) and make method
            // signiture 'throws XYZUNCHECKED EXCEPTION (for all methods until it has bubbled to controller and general controller advice can handle it)'
            throw new ConflictException(String.format("Account with email %s, already exists. " +
                    "Provide another request with different email", email));
        }
    }

    private void setRolesToUserToBeSaved(User userToSave, final UserRegisterRequest request) {
        userToSave.setRoles(new HashSet<>());
        // roles in the registerRequest will match the name column on the roles table,
        // bcs the request is validated by the custom validator
        request.getRoles().forEach(roleString -> {
            ERole[] enumValues = values();
            for (ERole enumVal : enumValues) {
                if (roleString.equalsIgnoreCase(enumVal.name())) {
                    Role userRole = roleRepository.findByName(enumVal)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found!"));
                    userToSave.addRole(userRole);
                }
            }
        });
    }

}