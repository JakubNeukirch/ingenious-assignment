package tech.stonks.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
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
                                viewModel = getViewModel()
                            )
                        }
                        composable("users/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                            //todo
                        }
                    }
                }
            }
        }
    }
}
