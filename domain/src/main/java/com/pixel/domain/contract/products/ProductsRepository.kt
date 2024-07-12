package com.pixel.domain.contract.products

import com.pixel.domain.common.Resource
import com.pixel.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>?>>
}
