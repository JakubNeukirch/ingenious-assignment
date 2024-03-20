package tech.stonks.presentation.user_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.stonks.presentation.shared.BaseViewModel
import tech.stonks.presentation.shared.model.BackPresentationDestination
import tech.stonks.presentation.user_details.model.UserDetailsError
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.presentation.user_details.repository.GetUserRepository

class UserDetailsViewModel(
    private val _userId: String,
    private val _getUserRepository: GetUserRepository,
) : BaseViewModel<UserDetailsState>(UserDetailsState.initial()) {
    fun onEntered() {
        modifyState { it.withLoading(true) }
        viewModelScope.launch {
            try {
                val user = _getUserRepository.getUser(_userId)
                modifyState { it.withUser(user).withLoading(false) }
            } catch (ex: Exception) {
                //todo handle more errors
                modifyState { it.withError(UserDetailsError.UNKNOWN_ERROR).withLoading(false) }
            }
        }
    }

    fun onBackPressed() {
        navigateTo(BackPresentationDestination)
    }
}
