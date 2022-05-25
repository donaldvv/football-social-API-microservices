package com.don.usersservice.mapper;

import com.don.usersservice.dto.UserDTO;
import com.don.usersservice.dto.request.UserRegisterRequest;
import com.don.usersservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles",
            expression= "java(" +
                    "user.getRoles().stream()" +
                    ".map(role -> role.getName().name())" +
                    ".collect(java.util.stream.Collectors.toSet()))")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "dateJoined", expression = "java(getNow())")
    @Mapping(target = "gender", expression = "java(registerRequest.getGender().toLowerCase())")
    @Mapping(target="roles", ignore = true)
    User registerRequestToUser(UserRegisterRequest registerRequest);

    List<UserDTO> usersToUserDTOs(List<User> users);

    /* // add them later
    @Named(value = "userOverview")
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userFirstname", source = "firstName")
    @Mapping(target = "userLastname", source = "lastName")
    @Mapping(target = "userPicture", source = "picture")
    UserOverviewDTO userToUserOverviewDTO(User user);

    @IterableMapping(qualifiedByName = "userOverview")
    List<UserOverviewDTO> usersToUserOverviewDTOs(List<User> user);


    */
    default LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
