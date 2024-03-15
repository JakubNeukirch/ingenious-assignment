package tech.stonks.presentation.users.model

import tech.stonks.presentation.common.model.UserPresentationModel

data class UsersState(
    val users: List<UserPresentationModel>,
    val isLoading: Boolean,
    val error: Error? = null
) {
    companion object {
        fun initial() = UsersState(emptyList(), false, null)
    }

    fun withUsers(users: List<UserPresentationModel>) = copy(users = users)
    fun withLoading(isLoading: Boolean) = copy(isLoading = isLoading)
    fun withError(error: Error?) = copy(error = error)


    enum class Error {
        NO_NETWORK,
        UNKNOWN
    }

}
