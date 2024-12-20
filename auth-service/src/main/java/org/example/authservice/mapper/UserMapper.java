package org.example.authservice.mapper;

import org.example.authservice.dto.user.BriefUserResponseDTO;
import org.example.authservice.dto.user.FullUserResponseDTO;
import org.example.authservice.shared.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    BriefUserResponseDTO toBriefUserResponseDTO(User user);

    FullUserResponseDTO toUserFullResponseDTO(User user);
}
