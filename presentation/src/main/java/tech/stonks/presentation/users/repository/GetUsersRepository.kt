package tech.stonks.presentation.users.repository

import tech.stonks.presentation.common.model.UserPresentationModel

interface GetUsersRepository {
    suspend fun getUsers(): List<UserPresentationModel>
}
