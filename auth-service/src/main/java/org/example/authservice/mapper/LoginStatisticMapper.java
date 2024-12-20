package org.example.authservice.mapper;

import org.example.authservice.dto.home.LoginStatisticResponseDTO;
import org.example.authservice.shared.entity.LoginStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginStatisticMapper {
    LoginStatisticMapper INSTANCE = Mappers.getMapper(LoginStatisticMapper.class);

    LoginStatisticResponseDTO toLoginStatisticResponseDTO(LoginStatistic loginStatistic);
}
