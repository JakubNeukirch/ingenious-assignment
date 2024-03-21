package tech.stonks.data.shared.mapper

import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.presentation.shared.model.UserPresentationModel

class UserPresentationMapper {
    fun map(user: UserDataModel): UserPresentationModel {
        return UserPresentationModel(
            id = user.id,
            login = user.login,
            avatarUrl = user.avatarUrl,
            followers = user.followers,
            location = user.location,
            bio = user.bio,
            realName = user.realName,
        )
    }
}
