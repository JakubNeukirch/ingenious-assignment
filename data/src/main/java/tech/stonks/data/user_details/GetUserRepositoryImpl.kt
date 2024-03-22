package tech.stonks.data.user_details

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.mapper.ExceptionPresentationMapper
import tech.stonks.data.shared.mapper.UserPresentationMapper
import tech.stonks.data.shared.mapper.runWithExceptionMapping
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.repository.GetUserRepository

class GetUserRepositoryImpl(
    private val _usersDataSource: UsersDataSource,
    private val _userMapper: UserPresentationMapper,
    private val _exceptionMapper: ExceptionPresentationMapper
) : GetUserRepository {
    override suspend fun getUser(userLogin: String): UserPresentationModel = _exceptionMapper.runWithExceptionMapping {
        _usersDataSource.getUser(userLogin).let(_userMapper::map)
    }
}
