package com.pixel.data.api.webServices

import com.pixel.data.api.model.Response
import retrofit2.http.GET

interface ProductWebServices {
    @GET("/products")
    suspend fun getProducts(): Response
}
