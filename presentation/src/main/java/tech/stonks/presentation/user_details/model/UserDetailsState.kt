package tech.stonks.presentation.user_details.model

import tech.stonks.presentation.shared.model.UserPresentationModel

data class UserDetailsState(
    val isLoading: Boolean,
    val user: UserPresentationModel?,
    val error: UserDetailsError?
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
    fun withError(error: UserDetailsError?) = copy(error = error)
}

enum class UserDetailsError {
    NETWORK_ERROR,
    UNKNOWN_ERROR
}
