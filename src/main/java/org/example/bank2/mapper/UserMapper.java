package org.example.bank2.mapper;

import org.example.bank2.dto.UserProjection;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "cards", ignore = true)
    User toEntity(UserRequest userRequest);

    UserProjection toProjection(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "cards", ignore = true)
    void updateUserFromDto(UserRequest updateUserRequest, @MappingTarget User user);
}
