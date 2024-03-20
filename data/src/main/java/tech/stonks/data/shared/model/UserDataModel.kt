package tech.stonks.data.shared.model

data class UserDataModel(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int?
)
