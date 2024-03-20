package tech.stonks.presentation.user_details

import tech.stonks.presentation.shared.BaseViewModel
import tech.stonks.presentation.user_details.model.UserDetailsState

class UserDetailsViewModel(
    private val _userId: String
) : BaseViewModel<UserDetailsState>(UserDetailsState.initial()) {
    fun onEntered() {
        modifyState { it.withLoading(true) }
    }
}
