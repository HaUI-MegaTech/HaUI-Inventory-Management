package org.example.productservice.service;


import org.example.productservice.dto.common.ImportDataRequestDTO;
import org.example.productservice.dto.common.ListIdsRequestDTO;
import org.example.productservice.dto.product.*;
import org.example.productservice.shared.global.GlobalResponseDTO;
import org.example.productservice.shared.global.NoPaginatedMeta;
import org.example.productservice.shared.global.PaginatedMeta;
import org.example.productservice.shared.global.PaginationRequestDTO;

import java.util.List;

public interface ProductService {
    // Get
    GlobalResponseDTO<NoPaginatedMeta, FullProductResponseDTO> getOne(Integer id);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getList(PaginationRequestDTO request, FilterProductRequestDTO filter);


    // Add
    GlobalResponseDTO<NoPaginatedMeta, BriefProductResponseDTO> addOne(AddProductRequestDTO request);


    // Update
    GlobalResponseDTO<NoPaginatedMeta, Void> updateOne(Integer id, UpdateProductRequestDTO request);

//    GlobalResponseDTO<NoPaginatedMeta, Void> updateListFromExcel(ImportDataRequestDTO request);


    // Hide
    GlobalResponseDTO<NoPaginatedMeta, Void> hideOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> hideList(ListIdsRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getHiddenList(PaginationRequestDTO request);


    // Expose
    GlobalResponseDTO<NoPaginatedMeta, Void> exposeOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> exposeList(ListIdsRequestDTO request);

    // Soft delete
    GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> softDeleteList(ListIdsRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getDeletedList(PaginationRequestDTO request);


    // Restore
    GlobalResponseDTO<NoPaginatedMeta, Void> restoreOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> restoreList(ListIdsRequestDTO request);


    // Hard delete
    GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, Void> hardDeleteList(ListIdsRequestDTO request);


    // Import
//    GlobalResponseDTO<NoPaginatedMeta, Void> importExcel(ImportDataRequestDTO request);
//
//    GlobalResponseDTO<NoPaginatedMeta, Void> importCsv(ImportDataRequestDTO request);
}
