package tech.stonks.datasource.shared.datasource

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.mapper.UserMapper
import tech.stonks.datasource.shared.service.UsersApiService

class UsersDataSourceImpl(
    private val _usersApi: UsersApiService,
    private val _userMapper: UserMapper
) : UsersDataSource {
    override suspend fun getUsers(): List<UserDataModel> {
        //todo add proper error mapping to DataException
        return _usersApi.getUsers().map(_userMapper::mapApiToData)
    }
}
