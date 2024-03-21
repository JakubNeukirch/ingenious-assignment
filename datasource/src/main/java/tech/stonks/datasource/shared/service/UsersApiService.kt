package tech.stonks.datasource.shared.service

import retrofit2.http.GET
import retrofit2.http.Path
import tech.stonks.datasource.shared.model.api.UserApiModel

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(): List<UserApiModel>

    @GET("users/{login}")
    suspend fun getUserByLogin(@Path("login") login: String): UserApiModel
}
