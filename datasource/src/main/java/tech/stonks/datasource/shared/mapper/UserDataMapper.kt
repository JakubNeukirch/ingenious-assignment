package tech.stonks.datasource.shared.mapper

import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.datasource.shared.model.api.UserApiModel

class UserDataMapper {
    fun mapApiToData(user: UserApiModel): UserDataModel {
        return UserDataModel(
            id = user.id.toString(),
            login = user.login,
            avatarUrl = user.avatarUrl,
            followers = user.followers,
            location = user.location,
            bio = user.bio,
            realName = user.name,
        )
    }
}
