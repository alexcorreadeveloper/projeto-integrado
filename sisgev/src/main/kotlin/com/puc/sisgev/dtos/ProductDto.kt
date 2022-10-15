package com.puc.sisgev.dtos

import com.fasterxml.jackson.annotation.JsonIgnore

data class ProductDto(
    @JsonIgnore
    val id: String? = null,
    val name: String,
    val description: String,
    val value: String,
    val quantity: Number
)
