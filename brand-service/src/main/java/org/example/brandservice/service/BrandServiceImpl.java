package org.example.brandservice.service;

import lombok.RequiredArgsConstructor;
import org.example.brandservice.dto.brand.BrandResponseDTO;
import org.example.brandservice.dto.brand.BrandStatisticResponseDTO;
import org.example.brandservice.mapper.BrandMapper;
import org.example.brandservice.repository.BrandRepository;
import org.example.brandservice.shared.constant.ErrorMessage;
import org.example.brandservice.shared.constant.PaginationConstant;
import org.example.brandservice.shared.constant.SuccessMessage;
import org.example.brandservice.shared.entity.Brand;
import org.example.brandservice.shared.exception.InvalidRequestParamException;
import org.example.brandservice.shared.exception.NotFoundException;
import org.example.brandservice.shared.global.*;
import org.example.brandservice.shared.util.MessageSourceUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository   brandRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BrandResponseDTO> getOne(Integer id) {
        Optional<Brand> found = brandRepository.findById(id);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BrandResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Brand.FOUND))
                        .build()
                )
                .data(BrandMapper.INSTANCE.toBrandResponseDTO(found.orElseThrow(
                                () -> new NotFoundException(ErrorMessage.Brand.NOT_FOUND))
                        )
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BrandResponseDTO>> getList(PaginationRequestDTO request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Brand> page = brandRepository.findAll(pageable);

        List<Brand> brands = page.getContent();
        return GlobalResponseDTO
                .<PaginatedMeta, List<BrandResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build()
                )
                .data(brands
                        .parallelStream()
                        .map(BrandMapper.INSTANCE::toBrandResponseDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalRevenue() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product ->
                                                (long) (product.getTotalSold() * (product.getCurrentPrice() - product.getImportPrice()))
                                        )
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalSold() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product -> product.getTotalSold())
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalView() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product -> product.getTotalViews())
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }
}
