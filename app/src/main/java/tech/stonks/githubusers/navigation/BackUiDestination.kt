package tech.stonks.githubusers.navigation

import androidx.navigation.NavController
import tech.stonks.ui.shared.navigation.UiDestination

class BackUiDestination(private val _navController: NavController) : UiDestination {
    override fun navigate() {
        _navController.popBackStack()
    }
}
