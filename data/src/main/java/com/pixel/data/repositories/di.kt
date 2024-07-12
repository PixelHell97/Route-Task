package com.pixel.data.repositories

import com.pixel.domain.contract.products.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindProductsRepo(impl: ProductsRepositoryImpl): ProductsRepository
}
