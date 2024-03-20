package tech.stonks.presentation.user_details.repository

import tech.stonks.presentation.shared.model.UserPresentationModel

interface GetUserRepository {
    suspend fun getUser(userId: String): UserPresentationModel
}
