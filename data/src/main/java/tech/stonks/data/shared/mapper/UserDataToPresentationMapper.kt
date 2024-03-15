package tech.stonks.data.shared.mapper

import tech.stonks.data.shared.model.UserDataModel
import tech.stonks.presentation.shared.model.UserPresentationModel

class UserDataToPresentationMapper {
    fun map(user: UserDataModel): UserPresentationModel {
        return UserPresentationModel(
            id = user.id,
            name = user.name
        )
    }
}
