package tech.stonks.presentation.users.model

import tech.stonks.presentation.shared.model.PresentationDestination

sealed class UsersPresentationDestination : PresentationDestination {
    data class UserDetails(val id: String) : UsersPresentationDestination()
}
