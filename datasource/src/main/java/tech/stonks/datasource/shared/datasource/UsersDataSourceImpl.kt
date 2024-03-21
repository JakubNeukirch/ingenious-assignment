package tech.stonks.datasource.shared.datasource

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.mapper.ExceptionDataMapper
import tech.stonks.datasource.shared.mapper.UserDataMapper
import tech.stonks.datasource.shared.mapper.runWithExceptionMapping
import tech.stonks.datasource.shared.service.UsersApiService

class UsersDataSourceImpl(
    private val _usersApi: UsersApiService,
    private val _userDataMapper: UserDataMapper,
    private val _exceptionDataMapper: ExceptionDataMapper
) : UsersDataSource {
    override suspend fun getUsers(): List<UserDataModel> = _exceptionDataMapper.runWithExceptionMapping {
        _usersApi.getUsers().map(_userDataMapper::mapApiToData)
    }

    override suspend fun getUser(userLogin: String): UserDataModel = _exceptionDataMapper.runWithExceptionMapping {
        _usersApi.getUserByLogin(userLogin).let(_userDataMapper::mapApiToData)
    }
}
