package tech.stonks.data.users

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.ExceptionPresentationMapper
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.data.shared.mapper.runWithExceptionMapping
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.repository.GetUsersRepository

class GetUsersRepositoryImpl(
    private val _userDataSource: UsersDataSource,
    private val _userPresentationMapper: UserPresentationMapper,
    private val _exceptionPresentationMapper: ExceptionPresentationMapper
) : GetUsersRepository {
    override suspend fun getUsers(): List<UserPresentationModel> =
        _exceptionPresentationMapper.runWithExceptionMapping {
            _userDataSource.getUsers().map(_userPresentationMapper::map)
    }
}
