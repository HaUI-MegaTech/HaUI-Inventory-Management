package org.example.brandservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {

    @GetMapping
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public String test() {
        return "Hello, brand service!";
    }
}
