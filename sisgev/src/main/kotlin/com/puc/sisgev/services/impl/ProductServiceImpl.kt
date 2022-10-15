package com.puc.sisgev.services.impl

import com.puc.sisgev.documents.Product
import com.puc.sisgev.repositories.ProductRepository
import com.puc.sisgev.services.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun createProduct(product: Product): Product? = productRepository.save(product)
    override fun listProducts(): List<Product> = productRepository.findAll().toList()
    override fun findProductById(id: String): Product = productRepository.findOne(id)
}
