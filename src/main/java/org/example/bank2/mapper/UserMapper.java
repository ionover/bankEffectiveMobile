package org.example.bank2.mapper;

import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;



@Mapper
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequest userRequest);
}
