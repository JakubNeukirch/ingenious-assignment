package tech.stonks.data.users

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.repository.GetUsersRepository

class GetUsersRepositoryImpl(
    private val _userDataSource: UsersDataSource,
    private val _userPresentationMapper: UserPresentationMapper
) : GetUsersRepository {
    override suspend fun getUsers(): List<UserPresentationModel> {
        return _userDataSource.getUsers().map(_userPresentationMapper::map)
    }
}
