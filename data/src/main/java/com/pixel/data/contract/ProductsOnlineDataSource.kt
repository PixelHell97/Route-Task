package com.pixel.data.contract

import com.pixel.domain.models.Product

interface ProductsOnlineDataSource {
    suspend fun getProducts(): List<Product>?
}
