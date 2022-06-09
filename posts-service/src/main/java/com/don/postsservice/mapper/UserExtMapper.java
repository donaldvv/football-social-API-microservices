package com.don.postsservice.mapper;

import com.don.postsservice.dto.UserDTO;
import com.don.postsservice.event.dto.user.UserMessage;
import com.don.postsservice.model.UserExt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Donald Veizi
 */
@Mapper(componentModel = "spring")
public interface UserExtMapper {

    @Mapping(target = "userIdExt", source = "userId")
    @Mapping(target="id", ignore = true)
    @Mapping(target="posts", ignore = true)
    UserExt userMessageToUserExt(UserMessage userMessage);

}
