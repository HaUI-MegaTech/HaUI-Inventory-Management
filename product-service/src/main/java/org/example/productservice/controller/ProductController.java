package org.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.common.ImportDataRequestDTO;
import org.example.productservice.dto.common.ListIdsRequestDTO;
import org.example.productservice.dto.product.*;
import org.example.productservice.service.ProductService;
import org.example.productservice.shared.constant.Endpoint;
import org.example.productservice.shared.global.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

//    @GetMapping
//    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
//    public String test() {
//        return "Hello, product service!";
//    }

    @GetMapping(Endpoint.V1.Product.GET_DETAIL_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, FullProductResponseDTO>> getOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getOne(productId));
    }

    @GetMapping(Endpoint.V1.Product.GET_ACTIVE_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getActiveList(
            @ModelAttribute FilterProductPaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getList(
                        PaginationRequestDTO
                                .builder()
                                .index(request.index())
                                .direction(request.direction())
                                .limit(request.limit())
                                .fields(request.fields())
                                .build(),
                        FilterProductRequestDTO
                                .builder()
                                .brandIds(request.brandIds())
                                .minPrice(request.minPrice())
                                .maxPrice(request.maxPrice())
                                .build()
                ));
    }

    @GetMapping(Endpoint.V1.Product.GET_HIDDEN_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getHiddenList(
            @ModelAttribute PaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getHiddenList(request));
    }

    @GetMapping(Endpoint.V1.Product.GET_DELETED_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>>> getDeletedList(
            @ModelAttribute PaginationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getDeletedList(request));
    }

    @PostMapping(Endpoint.V1.Product.ADD_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BriefProductResponseDTO>> addOne(
            @RequestBody AddProductRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addOne(request));
    }

//    @PostMapping(Endpoint.V1.Product.IMPORT_EXCEL)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> importExcel(
//            @ModelAttribute ImportDataRequestDTO request
//    ) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(productService.importExcel(request));
//    }
//
//    @PostMapping(Endpoint.V1.Product.IMPORT_CSV)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> importCsv(
//            @ModelAttribute ImportDataRequestDTO request
//    ) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(productService.importCsv(request));
//    }

    @PutMapping(Endpoint.V1.Product.UPDATE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> updateOne(
            @PathVariable Integer productId,
            @RequestBody UpdateProductRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateOne(productId, request));
    }

//    @PutMapping(Endpoint.V1.Product.UPDATE_LIST_FROM_EXCEL)
//    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> updateListFromExcel(
//            @ModelAttribute ImportDataRequestDTO request
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.updateListFromExcel(request));
//    }

    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> softDeleteOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.softDeleteOne(productId));
    }

    @PatchMapping(Endpoint.V1.Product.SOFT_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> softDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.softDeleteList(request));
    }

    @PatchMapping(Endpoint.V1.Product.RESTORE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> restoreOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.restoreOne(productId));
    }

    @PatchMapping(Endpoint.V1.Product.RESTORE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> restoreList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.restoreList(request));
    }

    @PatchMapping(Endpoint.V1.Product.HIDE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> hideOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hideOne(productId));
    }

    @PatchMapping(Endpoint.V1.Product.HIDE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> hideList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hideList(request));
    }

    @PatchMapping(Endpoint.V1.Product.EXPOSE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> exposeOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.exposeOne(productId));
    }

    @PatchMapping(Endpoint.V1.Product.EXPOSE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> exposeList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.exposeList(request));
    }

    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> hardDeleteOne(
            @PathVariable Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.hardDeleteOne(productId));
    }

    @DeleteMapping(Endpoint.V1.Product.HARD_DELETE_LIST)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, Void>> hardDeleteList(
            @RequestBody ListIdsRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.hardDeleteList(request));
    }
}
