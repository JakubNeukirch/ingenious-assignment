package tech.stonks.ui.page.user_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import tech.stonks.presentation.shared.model.UnknownPresentationException
import tech.stonks.presentation.shared.model.UserPresentationModel
import tech.stonks.presentation.user_details.UserDetailsViewModel
import tech.stonks.presentation.user_details.model.UserDetailsState
import tech.stonks.ui.R
import tech.stonks.ui.page.user_details.mapper.UserDetailsDestinationMapper
import tech.stonks.ui.widgets.BorderedContainer
import tech.stonks.ui.widgets.StatusView

@Composable
fun UserDetailsPage(viewModel: UserDetailsViewModel, destinationMapper: UserDetailsDestinationMapper) {
    val state by viewModel.state.observeAsState(UserDetailsState.initial())
    viewModel.destination.observe(LocalLifecycleOwner.current) { destination ->
        destinationMapper.map(destination).navigate()
    }

    LaunchedEffect(viewModel) {
        viewModel.onEntered()
    }

    Content(state, onBackPressed = viewModel::onBackPressed, onRefresh = viewModel::onRefresh)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Content(state: UserDetailsState, onBackPressed: () -> Unit, onRefresh: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = { Text(text = state.user?.login ?: stringResource(id = R.string.user_details_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null || state.user == null) {
            StatusView(
                exception = state.error ?: UnknownPresentationException(null),
                onRetry = onRefresh,
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                    state.user!!.location?.let {
                        Spacer(modifier = Modifier.height(16.dp))
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
                        Spacer(modifier = Modifier.height(16.dp))
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
                    Spacer(modifier = Modifier.height(16.dp))
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
        ),
        onBackPressed = {},
        onRefresh = {}
    )
}
