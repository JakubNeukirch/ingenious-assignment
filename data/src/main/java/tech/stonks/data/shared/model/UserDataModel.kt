package tech.stonks.data.shared.model

data class UserDataModel(
    val id: String,
    val login: String,
    val avatarUrl: String,
    val followers: Int?,
    val location: String? = null,
    val bio: String? = null,
    val realName: String? = null,
)
