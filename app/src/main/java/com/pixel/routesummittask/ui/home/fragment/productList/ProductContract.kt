package com.pixel.routesummittask.ui.home.fragment.productList

import androidx.lifecycle.LiveData
import com.pixel.domain.models.Product
import com.pixel.routesummittask.model.ViewMessage
import kotlinx.coroutines.flow.StateFlow

class ProductContract {
    interface ProductsViewModel {
        val state: StateFlow<State>
        val event: LiveData<Event>

        fun doAction(action: Action)
    }

    sealed class Action {
        data object LoadProducts : Action()
    }

    sealed class Event {
        data class ShowMessage(
            val message: ViewMessage,
        ) : Event()
    }

    sealed class State {
        data object Loading : State()

        data class Success(
            val productList: List<Product>? = null,
        ) : State()
    }
}
