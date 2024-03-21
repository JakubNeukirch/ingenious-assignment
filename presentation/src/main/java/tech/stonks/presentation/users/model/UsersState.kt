package tech.stonks.presentation.users.model

import tech.stonks.presentation.shared.model.PresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel

data class UsersState(
    val users: List<UserPresentationModel>,
    val isLoading: Boolean,
    val error: PresentationException? = null,
) {
    companion object {
        fun initial() = UsersState(emptyList(), false, null)
    }

    fun withUsers(users: List<UserPresentationModel>) = copy(users = users)
    fun withLoading(isLoading: Boolean) = copy(isLoading = isLoading)
    fun withError(error: PresentationException?) = copy(error = error)


    enum class Error {
        NO_NETWORK,
        TOO_MANY_REQUESTS,
        UNKNOWN
    }

}
