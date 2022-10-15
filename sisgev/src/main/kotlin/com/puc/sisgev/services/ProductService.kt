package com.puc.sisgev.services

import com.puc.sisgev.documents.Product

interface ProductService {
    fun createProduct(product: Product): Product?
    fun listProducts(): List<Product>
    fun findProductById(id: String): Product
}
