package org.example.bank2.mapper;

import org.example.bank2.dto.CardRequest;
import org.example.bank2.dto.UserRequest;
import org.example.bank2.entity.Card;
import org.example.bank2.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {

    CardMapper cardMapper = Mappers.getMapper(CardMapper.class);

    Card toEntity(CardRequest cardRequest);
}
