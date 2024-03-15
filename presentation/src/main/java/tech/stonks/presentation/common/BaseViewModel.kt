package tech.stonks.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {
    val state: LiveData<STATE> get() = _state
    private val _state = MutableLiveData<STATE>(initialState)

    protected fun setState(state: STATE) {
        _state.postValue(state)
    }

    protected fun modifyState(modifier: (state: STATE) -> STATE) {
        if (_state.value == null) throw IllegalStateException("State is not initialized")
        _state.postValue(modifier(_state.value!!))
    }
}