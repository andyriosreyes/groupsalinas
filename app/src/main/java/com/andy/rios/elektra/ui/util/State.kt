package com.andy.rios.elektra.ui.util

sealed class State{
    object Loading : State()
    object Empty : State()
    object NoLoading : State()

    data class Failed(val failure: Any) : State()
    data class Success(var data: Any) : State() {
        inline fun <reified T> responseTo() = data as T
    }
}