package tech.stonks.datasource.shared.mapper

import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.model.api.UserApiModel

class UserMapper {
    fun mapApiToData(user: UserApiModel): UserDataModel {
        return UserDataModel(
            id = user.id.toString(),
            name = user.login,
        )
    }
}
