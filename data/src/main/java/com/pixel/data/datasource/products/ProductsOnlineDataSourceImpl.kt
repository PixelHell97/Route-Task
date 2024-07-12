package com.pixel.data.datasource.products

import com.pixel.data.api.webServices.ProductWebServices
import com.pixel.data.contract.ProductsOnlineDataSource
import com.pixel.data.executeApi
import com.pixel.domain.models.Product
import javax.inject.Inject

class ProductsOnlineDataSourceImpl
    @Inject
    constructor(
        private val webServices: ProductWebServices,
    ) : ProductsOnlineDataSource {
        override suspend fun getProducts(): List<Product>? {
            val response =
                executeApi {
                    webServices.getProducts()
                }
            return response.products?.filterNotNull()?.map {
                it.toProduct()
            }
        }
    }
