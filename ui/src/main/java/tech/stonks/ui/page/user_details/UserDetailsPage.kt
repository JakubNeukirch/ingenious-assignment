package tech.stonks.ui.page.user_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.stonks.presentation.user_details.UserDetailsViewModel
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsPage(viewModel: UserDetailsViewModel) {
    val state by viewModel.state.observeAsState(UserDetailsState.initial())
    LaunchedEffect(viewModel) {
        viewModel.onEntered()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.user?.name ?: stringResource(id = R.string.user_details_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Text("$state")
        }
    }
}
