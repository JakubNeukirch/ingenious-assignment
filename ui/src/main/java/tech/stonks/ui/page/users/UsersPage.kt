package tech.stonks.ui.page.users

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.users.UsersViewModel
import tech.stonks.presentation.users.model.UsersState

@Composable
fun UsersPage(
    viewModel: UsersViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.onEntered()
    }

    val state: UsersState by viewModel.state.observeAsState(UsersState.initial())

    Content(state, onRefresh = viewModel::onRefresh)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: UsersState,
    onRefresh: () -> Unit,
) {
    val refreshState = rememberPullRefreshState(state.isLoading, onRefresh)

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(
                    state = refreshState,
                )
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.users.size) { index ->
                    val user = state.users[index]
                    UserItem(user = user)
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isLoading,
                state = refreshState
            )
        }
    }
}

@Composable
private fun UserItem(user: UserPresentationModel) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.07f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.05f)
                    )
                ),
            )
    ) {
        Row(
            modifier = Modifier
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Composable
@Preview
fun UsersPagePreview() {
    Content(
        state = UsersState(
            isLoading = false,
            users = listOf(
                UserPresentationModel("1", "John Doe", "https://picsum.photos/200/300", 10),
                UserPresentationModel("2", "Jane Doe", "https://picsum.photos/200/300", 20),
            )
        ),
        onRefresh = {}
    )
}
