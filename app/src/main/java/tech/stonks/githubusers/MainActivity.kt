package tech.stonks.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import tech.stonks.githubusers.navigation.user_details.UserDetailsDestinationMapperImpl
import tech.stonks.githubusers.navigation.users.UsersDestinationMapperImpl
import tech.stonks.ui.page.user_details.UserDetailsPage
import tech.stonks.ui.page.user_details.mapper.UserDetailsDestinationMapper
import tech.stonks.ui.page.users.UsersPage
import tech.stonks.ui.page.users.mapper.UsersDestinationMapper
import tech.stonks.ui.styles.theme.GithubUsersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val koin = get()
            val modules = remember {
                listOf(
                    module {
                        factory<NavController> { navController }
                        single<UsersDestinationMapper> { UsersDestinationMapperImpl(get()) }
                        single<UserDetailsDestinationMapper> { UserDetailsDestinationMapperImpl(get()) }
                    }
                )
            }
            koin.loadModules(modules)
            DisposableEffect(key1 = this) {
                onDispose {
                    koin.unloadModules(modules)
                }
            }
            GithubUsersTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "users") {
                        composable("users") {
                            UsersPage(
                                viewModel = getViewModel(),
                                destinationMapper = koin.get()
                            )
                        }
                        composable("users/{userId}") { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                                ?: throw IllegalStateException("Missing userId")
                            UserDetailsPage(
                                viewModel = koinViewModel(parameters = { parametersOf(userId) }),
                                destinationMapper = koin.get(),
                            )
                        }
                    }
                }
            }
        }
    }
}
