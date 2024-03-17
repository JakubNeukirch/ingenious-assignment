package tech.stonks.presentation.shared.model

data class UserPresentationModel(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val followers: Int?
)
