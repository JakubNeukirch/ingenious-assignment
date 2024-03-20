package tech.stonks.presentation.users

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.stonks.presentation.shared.BaseViewModel
import tech.stonks.presentation.users.model.UsersPresentationDestination
import tech.stonks.presentation.users.model.UsersState
import tech.stonks.presentation.users.repository.GetUsersRepository

class UsersViewModel(
    private val _getUsersRepository: GetUsersRepository
) : BaseViewModel<UsersState>(UsersState.initial()) {
    fun onEntered() {
        loadData()
    }

    fun onRefresh() {
        loadData()
    }

    fun onUserClicked(login: String) {
        navigateTo(UsersPresentationDestination.UserDetails(login))
    }

    private fun loadData() = viewModelScope.launch {
        modifyState { it.copy(isLoading = true) }
        try {
            _getUsersRepository.getUsers().let { users ->
                modifyState { it.copy(isLoading = false, users = users) }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            modifyState {
                it.copy(
                    isLoading = false,
                    error = UsersState.Error.UNKNOWN
                )
            }//todo add handling other error types
        }
    }
}
