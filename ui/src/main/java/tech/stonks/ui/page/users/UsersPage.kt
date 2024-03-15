package tech.stonks.ui.page.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import tech.stonks.presentation.users.UsersViewModel

@Composable
fun UsersPage(
    viewModel: UsersViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.onEntered()
    }

    val state by viewModel.state.observeAsState()

    Scaffold {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Text("$state")
        }
    }
}
