package com.example.SpringProject1.web.mappers;

import com.example.SpringProject1.domain.user.User;
import com.example.SpringProject1.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto Dto);
}
