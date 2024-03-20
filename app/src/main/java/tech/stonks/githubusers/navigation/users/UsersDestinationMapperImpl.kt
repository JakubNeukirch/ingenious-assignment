package tech.stonks.githubusers.navigation.users

import androidx.navigation.NavController
import tech.stonks.githubusers.navigation.BackUiDestination
import tech.stonks.presentation.shared.model.BackPresentationDestination
import tech.stonks.presentation.shared.model.PresentationDestination
import tech.stonks.presentation.users.model.UsersPresentationDestination
import tech.stonks.ui.page.users.mapper.UsersDestinationMapper
import tech.stonks.ui.shared.navigation.UiDestination

class UsersDestinationMapperImpl(private val _navController: NavController) : UsersDestinationMapper {
    override fun map(destination: PresentationDestination): UiDestination {
        return when (destination) {
            is UsersPresentationDestination.UserDetails -> {
                UserDetailsUiDestination(_navController, destination.userLogin)
            }

            is BackPresentationDestination -> {
                BackUiDestination(_navController)
            }

            else -> {
                throw IllegalArgumentException("Unknown destination: $destination")
            }
        }
    }
}

private class UserDetailsUiDestination(private val _navController: NavController, private val _userLogin: String) :
    UiDestination {
    override fun navigate() {
        _navController.navigate("users/$_userLogin")
    }
}
