package com.don.usersservice;

import com.don.usersservice.model.Role;
import com.don.usersservice.model.User;
import com.don.usersservice.model.enums.EGender;
import com.don.usersservice.model.enums.ERole;
import com.don.usersservice.repository.FootballClubRepository;
import com.don.usersservice.repository.RoleRepository;
import com.don.usersservice.repository.UserRepository;
import com.don.usersservice.service.UserEventPrepare;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Donald Veizi
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)  // qe mos kem nevoje ti bej Open+Close Mocks
public class DBPopulator {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FootballClubRepository footballClubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEventPrepare userEventPrepare;


    @Test
    @Rollback(value = false)
    void populate_ROLE_USER_CLUBS() {
        long rolesCount = roleRepository.count();
        if (rolesCount == 0) {
            Role roleUser = new Role();
            roleUser.setName(ERole.ROLE_USER);
            Role roleRecruiter = new Role();
            roleRecruiter.setName(ERole.ROLE_RECRUITER);
            roleRepository.saveAndFlush(roleRecruiter);
            roleRepository.saveAndFlush(roleUser);
        }

        // -----------------------------------------------------------------------------------------

        // create users and produce event for creation of user reference in the posts microservice
        long usersCount = userRepository.count();
        if (usersCount == 0) {
            User user1 = getUser(
                    "ronaldo@gmail.com", "Cristiano", "Ronaldo", EGender.MALE,
                    "Portugal", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save1 = userRepository.save(user1);
            userEventPrepare.produceUserCreatedEvent(save1);

            User user2 = getUser(
                    "messio@gmail.com", "Leo", "Messi", EGender.MALE,
                    "Paris", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save2 = userRepository.save(user2);
            userEventPrepare.produceUserCreatedEvent(save2);

            User user3 = getUser(
                    "zidane@gmail.com", "Zinedine", "Zidane", EGender.MALE,
                    "France", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save3 = userRepository.save(user3);
            userEventPrepare.produceUserCreatedEvent(save3);

            User user4 = getUser(
                    "ronaldinho@gmail.com", "Ronaldinho", "Gaucho", EGender.MALE,
                    "Brazil", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save4 = userRepository.save(user4);
            userEventPrepare.produceUserCreatedEvent(save4);

            User user5 = getUser(
                    "best@gmail.com", "George", "Best", EGender.MALE,
                    "Portugal", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save5 = userRepository.save(user5);
            userEventPrepare.produceUserCreatedEvent(save5);

            User user6 = getUser(
                    "neymar@gmail.com", "Neymar", "Santos", EGender.MALE,
                    "Paris, France", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_USER
            );
            User save6 = userRepository.save(user6);
            userEventPrepare.produceUserCreatedEvent(save6);

            User recruiter1 = getUser(
                    "jon@gmail.com", "Jon", "Doe", EGender.MALE,
                    "Paris, France", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_RECRUITER
            );
            User rec1 = userRepository.save(recruiter1);
            userEventPrepare.produceUserCreatedEvent(rec1);

            User recruiter2 = getUser(
                    "jake@gmail.com", "Jake", "Johnson", EGender.MALE,
                    "Texas, US", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_RECRUITER
            );
            User rec2 = userRepository.save(recruiter2);
            userEventPrepare.produceUserCreatedEvent(rec2);

            User recruiter3 = getUser(
                    "june@gmail.com", "June ", "Sullivan", EGender.FEMALE,
                    "New York, US", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_RECRUITER
            );
            User rec3 = userRepository.save(recruiter3);
            userEventPrepare.produceUserCreatedEvent(rec3);

            User recruiter4 = getUser(
                    "jessika@gmail.com", "Jessika", "Seiler", EGender.FEMALE,
                    "Frankfurt, Germany", LocalDate.of(1988, 2, 5), "123456", ERole.ROLE_RECRUITER
            );
            User rec4 = userRepository.save(recruiter4);
            userEventPrepare.produceUserCreatedEvent(rec4);
        }

        // -----------------------------------------------------------------------------------------



    }

    private User getUser(String email, String firstName, String lastName, EGender gender,
                         String address, LocalDate birthday, String password, ERole role) {

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setBirthday(birthday);
        user.setDateJoined(LocalDateTime.now());
        user.setGender(gender.name());
        user.setPassword(getEncryptedPass(password));
        if (role.equals(ERole.ROLE_USER))
            user.setRoles(getUserRole());
        else
            user.setRoles(getRecruiterRole());
        // other nullable fields we can just ignore. They can be added when updating profile

        return user;
    }

    private Collection<Role> getUserRole() {
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();
        HashSet<Role> set = new HashSet<>();
        set.add(role);
        return set;
    }

    private Collection<Role> getRecruiterRole() {
        Role role = roleRepository.findByName(ERole.ROLE_RECRUITER).get();
        HashSet<Role> set = new HashSet<>();
        set.add(role);
        return set;
    }

    private String getEncryptedPass(String password) {
        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
