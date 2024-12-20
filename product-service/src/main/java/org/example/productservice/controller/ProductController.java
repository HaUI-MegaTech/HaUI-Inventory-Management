package org.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping
    @PreAuthorize("@appAuthorizer.authorize(authentication, this, @requestContextUtils.getCurrentHttpRequest())")
    public String test() {
        return "Hello, product service!";
    }
}
