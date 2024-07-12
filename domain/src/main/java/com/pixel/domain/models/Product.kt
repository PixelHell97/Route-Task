package com.pixel.domain.models

data class Product(
    val id: Int? = null,
    val images: List<String?>? = null,
    val title: String? = null,
    val rating: Double? = null,
    val price: Double? = null,
    val discountPercentage: Double? = null,
)
