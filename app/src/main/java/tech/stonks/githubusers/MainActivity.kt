package tech.stonks.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import tech.stonks.githubusers.navigation.user_details.UserDetailsDestinationMapperImpl
import tech.stonks.githubusers.navigation.users.UsersDestinationMapperImpl
import tech.stonks.ui.page.user_details.UserDetailsPage
import tech.stonks.ui.page.users.UsersPage
import tech.stonks.ui.styles.theme.GithubUsersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GithubUsersTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "users") {
                        composable("users") {
                            UsersPage(
                                viewModel = getViewModel(),
                                destinationMapper = remember {
                                    UsersDestinationMapperImpl(navController)
                                }
                            )
                        }
                        composable("users/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                                ?: throw IllegalStateException("Missing userId")
                            UserDetailsPage(
                                viewModel = koinViewModel(parameters = { parametersOf(userId) }),
                                destinationMapper = remember {
                                    UserDetailsDestinationMapperImpl(navController)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
