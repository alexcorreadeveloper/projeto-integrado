package com.puc.sisgev.repositories

import com.puc.sisgev.documents.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String>
