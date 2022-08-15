package com.example.orderapi.product.adapter.presentation.api

import com.example.orderapi.product.adapter.presentation.dto.ProductCreateRequest
import com.example.orderapi.product.domain.port.`in`.ProductCreateUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
class ProductController(
    private val productCreateUseCase: ProductCreateUseCase,
) {
    @PostMapping("/api/v1/products")
    fun registerProduct(@RequestBody productCreateRequest: ProductCreateRequest): ResponseEntity<URI> {
        val productId = productCreateUseCase.registeItem(productCreateRequest.toCommand())

        val productRegisterUri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(productId)
            .toUri()

        return ResponseEntity.ok(productRegisterUri)
    }
}