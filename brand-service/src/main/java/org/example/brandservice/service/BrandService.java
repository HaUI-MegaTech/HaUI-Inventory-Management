package org.example.brandservice.service;



import org.example.brandservice.dto.brand.BrandResponseDTO;
import org.example.brandservice.dto.brand.BrandStatisticResponseDTO;
import org.example.brandservice.shared.global.GlobalResponseDTO;
import org.example.brandservice.shared.global.NoPaginatedMeta;
import org.example.brandservice.shared.global.PaginatedMeta;
import org.example.brandservice.shared.global.PaginationRequestDTO;

import java.util.List;

public interface BrandService {
    GlobalResponseDTO<NoPaginatedMeta, BrandResponseDTO> getOne(Integer id);

    GlobalResponseDTO<PaginatedMeta, List<BrandResponseDTO>> getList(PaginationRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalRevenue();

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalSold();

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalView();
}
