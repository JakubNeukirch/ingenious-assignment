package tech.stonks.data.shared.datasource

import tech.stonks.data.shared.model.UserDataModel

interface UsersDataSource {
    suspend fun getUsers(): List<UserDataModel>
    suspend fun getUser(id: String): UserDataModel
}
