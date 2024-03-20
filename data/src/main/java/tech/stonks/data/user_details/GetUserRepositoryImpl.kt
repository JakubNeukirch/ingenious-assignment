package tech.stonks.data.user_details

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.repository.GetUserRepository

class GetUserRepositoryImpl(
    private val _usersDataSource: UsersDataSource,
    private val _userMapper: UserPresentationMapper,
) : GetUserRepository {
    override suspend fun getUser(userId: String): UserPresentationModel {
        return _usersDataSource.getUser(userId).let(_userMapper::map)
    }
}
