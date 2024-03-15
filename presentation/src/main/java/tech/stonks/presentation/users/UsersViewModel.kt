package tech.stonks.presentation.users

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.stonks.presentation.common.BaseViewModel
import tech.stonks.presentation.common.model.UserPresentationModel
import tech.stonks.presentation.users.model.UsersState

class UsersViewModel : BaseViewModel<UsersState>(UsersState.initial()) {
    fun onEntered() = viewModelScope.launch {
        modifyState { it.copy(isLoading = true) }
        delay(1000)
        modifyState {
            it.copy(
                isLoading = false, users = listOf(
                    UserPresentationModel("1", "Jakub"),
                    UserPresentationModel("2", "Kamil"),
                    UserPresentationModel("3", "Wojtek"),
                )
            )
        }
    }
}
