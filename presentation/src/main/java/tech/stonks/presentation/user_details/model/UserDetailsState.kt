package tech.stonks.presentation.user_details.model

import tech.stonks.presentation.shared.model.PresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel

data class UserDetailsState(
    val isLoading: Boolean,
    val user: UserPresentationModel?,
    val error: PresentationException?
) {
    companion object {
        fun initial() = UserDetailsState(
            isLoading = false,
            user = null,
            error = null
        )
    }

    fun withLoading(isLoading: Boolean) = copy(isLoading = isLoading)
    fun withUser(user: UserPresentationModel?) = copy(user = user)
    fun withError(error: PresentationException?) = copy(error = error)
}
