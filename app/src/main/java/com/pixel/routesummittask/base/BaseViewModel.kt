package com.pixel.routesummittask.base

import androidx.lifecycle.ViewModel
import com.pixel.domain.common.InternetConnectionError
import com.pixel.domain.common.Resource
import com.pixel.routesummittask.model.ViewMessage

open class BaseViewModel : ViewModel() {
    fun <T> extractViewMessage(resource: Resource<T>): ViewMessage? =
        when (resource) {
            is Resource.ServerFail -> {
                ViewMessage(
                    message = resource.error.message ?: "Something went wrong",
                )
            }

            is Resource.Fail -> {
                when (resource.exception) {
                    is InternetConnectionError -> {
                        ViewMessage(
                            message = "Please check your internet connection",
                        )
                    }

                    else -> {
                        ViewMessage(
                            message = resource.exception.message ?: "Something went wrong",
                        )
                    }
                }
            }

            else -> null
        }
}
