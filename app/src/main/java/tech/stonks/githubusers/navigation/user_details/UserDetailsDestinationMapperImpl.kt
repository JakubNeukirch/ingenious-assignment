package tech.stonks.githubusers.navigation.user_details

import androidx.navigation.NavController
import tech.stonks.githubusers.navigation.BackUiDestination
import tech.stonks.presentation.shared.model.BackPresentationDestination
import tech.stonks.presentation.shared.model.PresentationDestination
import tech.stonks.ui.page.user_details.mapper.UserDetailsDestinationMapper
import tech.stonks.ui.shared.navigation.UiDestination

class UserDetailsDestinationMapperImpl(private val _navController: NavController) : UserDetailsDestinationMapper {
    override fun map(destination: PresentationDestination): UiDestination {
        return when (destination) {
            is BackPresentationDestination -> {
                BackUiDestination(_navController)
            }

            else -> {
                throw IllegalArgumentException("Unknown destination: $destination")
            }
        }
    }
}
