package org.example.productservice.dto.product;

import lombok.Builder;
import org.example.productservice.dto.ImageDTO;
import org.example.productservice.shared.entity.SimilarProduct;

import java.util.List;

@Builder
public record FullProductResponseDTO(
        Integer id,
        String mainImageUrl,
        String name,
        Float oldPrice,
        Float currentPrice,
        Integer discountPercent,
        Integer remaining,

        String processor,
        Integer cores,
        Integer threads,
        String frequency,
        String boostFrequency,
        String cacheCapacity,

        String memoryCapacity,
        String memoryType,
        String memoryBus,
        String maxMemoryCapacity,
        String storage,

        String displaySize,
        String resolution,
        String refreshRate,
        String colorGamut,
        String panelType,
        String touchScreen,

        String graphicsCard,
        String soundTechnology,

        String wirelessConnectivity,
        String sdCard,
        String webcam,
        String coolingFan,
        String miscFeature,
        String backlitKeyboard,

        String dimensionWeight,
        String material,

        String batteryCapacity,
        String chargerCapacity,
        String os,
        Integer launchDate,

        Float averageRating,
        Integer feedbacksCount,
        Integer totalSold,

        BrandInFullProductResponseDTO brand,

        String article,

        List<ImageDTO> images,

        List<SimilarProduct> similarProducts
) {
}
