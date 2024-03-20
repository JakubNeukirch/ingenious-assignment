package tech.stonks.datasource.shared.service

import retrofit2.http.GET
import tech.stonks.datasource.shared.model.api.UserApiModel

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

    @GET("users/{id}")
    suspend fun getUserById(id: String): UserApiModel
}
