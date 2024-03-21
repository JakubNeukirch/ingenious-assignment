package tech.stonks.presentation.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.stonks.presentation.shared.model.PresentationDestination
import tech.stonks.presentation.shared.model.SingleLiveEvent

abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {
    private val _state = MutableLiveData<STATE>(initialState)
    val state: LiveData<STATE> get() = _state

    private val _destination = SingleLiveEvent<PresentationDestination>()
    val destination: LiveData<PresentationDestination> get() = _destination

    protected fun modifyState(modifier: (state: STATE) -> STATE) {
        if (_state.value == null) throw IllegalStateException("State is not initialized")
        _state.value = modifier(_state.value!!)
    }

    protected fun navigateTo(destination: PresentationDestination) {
        _destination.value = destination
    }
}
