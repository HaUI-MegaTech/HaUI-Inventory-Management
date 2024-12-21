package org.example.productservice.mapper;

import org.example.productservice.dto.product.AddProductRequestDTO;
import org.example.productservice.dto.product.BriefProductResponseDTO;
import org.example.productservice.dto.product.FullProductResponseDTO;
import org.example.productservice.shared.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings(
            value = {
                    @Mapping(target = "weight", source = "shortWeight"),
                    @Mapping(target = "ram", source = "memoryCapacity"),
                    @Mapping(target = "newPrice", source = "currentPrice"),
                    @Mapping(target = "display", source = "shortDisplay"),
                    @Mapping(target = "card", source = "shortCard"),
                    @Mapping(target = "battery", source = "batteryCapacity"),
            }
    )
    BriefProductResponseDTO toBriefProductResponseDTO(Product product);

    FullProductResponseDTO toFullProductResponseDTO(Product product);

    Product toProduct(AddProductRequestDTO request);

}
