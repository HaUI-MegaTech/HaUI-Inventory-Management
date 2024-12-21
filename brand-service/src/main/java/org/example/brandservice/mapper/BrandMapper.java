package org.example.brandservice.mapper;

import org.example.brandservice.dto.brand.BrandResponseDTO;
import org.example.brandservice.shared.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandResponseDTO toBrandResponseDTO(Brand brand);
}
