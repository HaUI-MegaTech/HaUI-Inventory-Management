package org.example.userservice.mapper;

import org.example.userservice.dto.user.BriefUserResponseDTO;
import org.example.userservice.dto.user.FullUserResponseDTO;
import org.example.userservice.shared.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    BriefUserResponseDTO toBriefUserResponseDTO(User user);

    FullUserResponseDTO toUserFullResponseDTO(User user);
}
