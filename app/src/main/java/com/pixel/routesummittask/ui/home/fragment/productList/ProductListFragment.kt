package com.pixel.routesummittask.ui.home.fragment.productList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.pixel.routesummittask.R
import com.pixel.routesummittask.base.BaseFragment
import com.pixel.routesummittask.databinding.FragmentProductListBinding
import com.pixel.routesummittask.ui.home.fragment.productList.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductsListViewModel>() {
    private val mViewModel: ProductsListViewModel by viewModels()

    override fun initViewModel(): ProductsListViewModel = mViewModel as ProductsListViewModel

    override fun getLayoutId(): Int = R.layout.fragment_product_list

    private val productsAdapter by lazy { ProductsAdapter(requireContext()) }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
        loadData()
    }

    private fun loadData() {
        viewModel.doAction(ProductContract.Action.LoadProducts)
    }

    private fun observeData() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ProductContract.Event.ShowMessage -> {
                    showErrorView(event.message.message)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    ProductContract.State.Loading -> showLoadingView()
                    is ProductContract.State.Success -> {
                        Log.e("productList", "${state.productList?.size}")
                        state.productList?.let { response ->
                            productsAdapter.bindProducts(response)
                            showSuccessView()
                        }
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.categoryProductsRv.adapter = productsAdapter
    }

    private fun showLoadingView() {
        binding.productsShimmerViewContainer.isVisible = true
        binding.productsShimmerViewContainer.startShimmer()
        binding.errorView.isVisible = false
        binding.successView.isVisible = false
    }

    private fun showSuccessView() {
        binding.successView.isVisible = true
        binding.errorView.isVisible = false
        binding.productsShimmerViewContainer.isVisible = false
        binding.productsShimmerViewContainer.stopShimmer()
    }

    private fun showErrorView(message: String) {
        binding.errorView.isVisible = true
        binding.successView.isVisible = false
        binding.productsShimmerViewContainer.isVisible = false
        binding.productsShimmerViewContainer.stopShimmer()
        binding.errorMessage.text = message
        binding.tryAgainBtn.setOnClickListener {
            loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.productsShimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        binding.productsShimmerViewContainer.stopShimmer()
        super.onPause()
    }
}
