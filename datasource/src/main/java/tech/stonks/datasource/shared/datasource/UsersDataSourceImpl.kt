package tech.stonks.datasource.shared.datasource

import tech.stonks.data.shared.datasource.UsersDataSource
import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.mapper.UserDataMapper
import tech.stonks.datasource.shared.service.UsersApiService

class UsersDataSourceImpl(
    private val _usersApi: UsersApiService,
    private val _userDataMapper: UserDataMapper
) : UsersDataSource {
    override suspend fun getUsers(): List<UserDataModel> {
        //todo add proper error mapping to DataException
        return _usersApi.getUsers().map(_userDataMapper::mapApiToData)
    }

    override suspend fun getUser(userLogin: String): UserDataModel {
        return _usersApi.getUserByLogin(userLogin).let(_userDataMapper::mapApiToData)
    }
}
