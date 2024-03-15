package tech.stonks.presentation.users.model

import tech.stonks.presentation.common.model.UserPresentationModel

data class UsersState(
    val users: List<UserPresentationModel>,
    val isLoading: Boolean
) {
    companion object {
        fun initial() = UsersState(emptyList(), false)
    }

    fun withUsers(users: List<UserPresentationModel>) = copy(users = users)
    fun withLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
