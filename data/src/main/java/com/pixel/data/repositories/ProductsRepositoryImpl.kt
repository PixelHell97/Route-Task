package com.pixel.data.repositories

import com.pixel.data.contract.ProductsOnlineDataSource
import com.pixel.data.toFlow
import com.pixel.domain.common.Resource
import com.pixel.domain.contract.products.ProductsRepository
import com.pixel.domain.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl
    @Inject
    constructor(
        private val productsOnlineDataSource: ProductsOnlineDataSource,
    ) : ProductsRepository {
        override suspend fun getProducts(): Flow<Resource<List<Product>?>> =
            toFlow {
                productsOnlineDataSource.getProducts()
            }
    }
