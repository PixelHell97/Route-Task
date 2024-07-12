package com.pixel.data.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.pixel.domain.models.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDto(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("images")
    val images: List<String?>? = null,
    @field:SerializedName("rating")
    val rating: Double? = null,
    @field:SerializedName("title")
    val title: String? = null,
    @field:SerializedName("price")
    val price: Double? = null,
    @field:SerializedName("discountPercentage")
    val discountPercentage: Double? = null,
) : Parcelable {
    fun toProduct(): Product =
        Product(
            id = id,
            images = images,
            rating = rating,
            title = title,
            price = price,
            discountPercentage = discountPercentage,
        )
}
