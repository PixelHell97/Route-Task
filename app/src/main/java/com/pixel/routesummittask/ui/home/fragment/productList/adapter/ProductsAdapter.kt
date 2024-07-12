package com.pixel.routesummittask.ui.home.fragment.productList.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pixel.domain.models.Product
import com.pixel.routesummittask.R
import com.pixel.routesummittask.databinding.ItemProductBinding
import com.pixel.routesummittask.utils.MyDiffUtil
import java.text.DecimalFormat

class ProductsAdapter(
    private val context: Context,
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var products: List<Product?> = emptyList()

    inner class ViewHolder(
        private val context: Context,
        private val itemProductBinding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(itemProductBinding.root) {
        fun bind(product: Product?) {
            itemProductBinding.product = product
            itemProductBinding.executePendingBindings()
            if (product?.discountPercentage != null) {
                itemProductBinding.productPrice.text =
                    context.getString(
                        R.string.egp,
                        getPriceAfterDiscount(
                            product.price!!,
                            product.discountPercentage!!,
                        ),
                    )
                itemProductBinding.productOldPrice.isVisible = true
                itemProductBinding.productOldPrice.text =
                    context.getString(R.string.egp, DecimalFormat("#,##0.00").format(product.price))
                itemProductBinding.productOldPrice.paintFlags =
                    itemProductBinding.productOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemProductBinding.productPrice.text =
                    context.getString(
                        R.string.egp,
                        DecimalFormat("#,##0.00").format(product?.price),
                    )
                itemProductBinding.productOldPrice.isVisible = false
            }
            itemProductBinding.reviewValueTv.text = "(${product?.rating})"
        }

        private fun getPriceAfterDiscount(
            price: Double,
            discount: Double,
        ): String {
            val discountValue = (discount / 100) * price
            val priceAfterDiscount = (price - discountValue)

            val decimalFormat = DecimalFormat("#,##0.00")
            return decimalFormat.format(priceAfterDiscount)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            context,
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun getItemCount() = products.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val product = products[position]
        holder.bind(product)
    }

    fun bindProducts(newProducts: List<Product?>) {
        val diffUtil =
            MyDiffUtil(products.filterNotNull(), newProducts.filterNotNull()) { oldItem, newItem ->
                when {
                    oldItem.images != newItem.images -> false
                    oldItem.rating != newItem.rating -> false
                    oldItem.title != newItem.title -> false
                    oldItem.discountPercentage != newItem.discountPercentage -> false
                    oldItem.price != newItem.price -> false
                    oldItem.id != newItem.id -> false
                    else -> true
                }
            }
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.products = newProducts
        diffResult.dispatchUpdatesTo(this)
    }
}
