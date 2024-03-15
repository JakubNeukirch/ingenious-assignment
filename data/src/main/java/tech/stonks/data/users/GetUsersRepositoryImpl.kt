package tech.stonks.data.users

import kotlinx.coroutines.delay
import tech.stonks.presentation.common.model.UserPresentationModel
import tech.stonks.presentation.users.repository.GetUsersRepository

class GetUsersRepositoryImpl : GetUsersRepository {
    override suspend fun getUsers(): List<UserPresentationModel> {
        delay(1000)
        return listOf(
            UserPresentationModel("1", "John Doe"),
            UserPresentationModel("2", "Jane Doe"),
        )
    }
}
