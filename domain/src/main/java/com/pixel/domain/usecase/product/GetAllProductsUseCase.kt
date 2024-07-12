package com.pixel.domain.usecase.product

import com.pixel.domain.common.Resource
import com.pixel.domain.contract.products.ProductsRepository
import com.pixel.domain.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase
    @Inject
    constructor(
        private val productsRepository: ProductsRepository,
    ) {
        suspend operator fun invoke(): Flow<Resource<List<Product>?>> = productsRepository.getProducts()
    }
