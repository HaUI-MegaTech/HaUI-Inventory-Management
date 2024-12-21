package org.example.brandservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.brandservice.service.BrandService;
import org.example.brandservice.shared.constant.Endpoint;
import org.example.brandservice.shared.global.PaginationRequestDTO;
import org.example.brandservice.shared.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping(Endpoint.V1.Brand.GET_ONE)
    public ResponseEntity<?> getOne(@PathVariable Integer brandId) {
        return ResponseUtil.ok(brandService.getOne(brandId));
    }

    @GetMapping(Endpoint.V1.Brand.GET_ACTIVE_LIST)
    public ResponseEntity<?> getActiveList(@ModelAttribute PaginationRequestDTO request) {
        return ResponseUtil.ok(brandService.getList(request));
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_REVENUE)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<?> getTotalRevenue() {
        return ResponseUtil.ok(brandService.getTotalRevenue());
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_SOLD)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<?> getTotalSold() {
        return ResponseUtil.ok(brandService.getTotalSold());
    }

    @GetMapping(Endpoint.V1.Brand.GET_TOTAL_VIEW)
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public ResponseEntity<?> getTotalView() {
        return ResponseUtil.ok(brandService.getTotalView());
    }
}
