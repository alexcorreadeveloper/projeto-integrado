package com.puc.sisgev.controllers

import com.puc.sisgev.documents.Product
import com.puc.sisgev.dtos.ProductDto
import com.puc.sisgev.services.ProductService
import com.puc.sisgev.utils.QrCodeGenerator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(private val productService: ProductService) {

    @PostMapping("/products")
    fun add(@RequestBody product: ProductDto): ProductDto {
        val productSaved = productService.createProduct(product.toProduct())
        val url = "https://8b49-179-108-21-32.ngrok.io/product?id=${productSaved?.id}"
        val qrCode = QrCodeGenerator().generator(productSaved?.id!!, productSaved.name, url)
        productSaved.qrCode = qrCode
        productService.createProduct(productSaved)
        return productSaved.toProductDto()
    }

    @GetMapping("/products")
    fun list(): List<ProductDto> {
        return productService.listProducts().map { it.toProductDto() }
    }

    @GetMapping("/product")
    fun findById(@RequestParam id: String): ProductDto {
        return productService.findProductById(id).toProductDto()
    }

    private fun Product.toProductDto(): ProductDto =
        ProductDto(
            id = id,
            name = name,
            description = description,
            value = value,
            quantity = quantity
        )

    private fun ProductDto.toProduct(qrCode: ByteArray? = null): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            value = value,
            quantity = quantity,
            qrCode = qrCode
        )
    }
}
