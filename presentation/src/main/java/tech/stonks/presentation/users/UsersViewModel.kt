package tech.stonks.presentation.users

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.stonks.presentation.shared.BaseViewModel
import tech.stonks.presentation.shared.model.PresentationException
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
        modifyState { it.withLoading(isLoading = true) }
        try {
            val users = withContext(Dispatchers.IO) { _getUsersRepository.getUsers() }
            modifyState {
                it.withLoading(false)
                    .withUsers(users)
                    .withError(null)
            }
        } catch (ex: PresentationException) {
            ex.printStackTrace()
            modifyState {
                it.withLoading(false)
                    .withError(ex)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw IllegalStateException("Unhandled exception", ex)
        }
    }
}
