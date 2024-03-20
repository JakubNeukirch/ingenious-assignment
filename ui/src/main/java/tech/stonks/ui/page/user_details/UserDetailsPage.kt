package tech.stonks.ui.page.user_details

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.UserDetailsViewModel
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.ui.R
import tech.stonks.ui.widgets.BorderedContainer

@Composable
fun UserDetailsPage(viewModel: UserDetailsViewModel) {
    val state by viewModel.state.observeAsState(UserDetailsState.initial())
    //todo add navigation
    LaunchedEffect(viewModel) {
        viewModel.onEntered()
    }
    Log.d("Content", "state: ${state}")

    Content(state)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Content(state: UserDetailsState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.user?.login ?: stringResource(id = R.string.user_details_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) {
        if (state.isLoading || state.user == null) {
            CircularProgressIndicator()
        } else {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    BorderedContainer(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.3f)
                                    .aspectRatio(1f),
                                model = state.user!!.avatarUrl, contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = 0.7f)
                                    .padding(start = 16.dp)
                            ) {
                                Text(text = state.user!!.login, style = MaterialTheme.typography.titleMedium)
                                state.user!!.realName?.let {
                                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    state.user!!.location?.let {
                        BorderedContainer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .size(24.dp)
                                )
                                Text(text = it, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    state.user!!.bio?.let {
                        BorderedContainer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Column {
                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    text = stringResource(id = R.string.user_details_bio),
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(text = it, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserDetailsPagePreview() {
    Content(
        state = UserDetailsState(
            isLoading = false,
            user = UserPresentationModel(
                login = "login",
                realName = "realName",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                id = "1",
                followers = 1
            ),
            error = null
        )
    )
}
