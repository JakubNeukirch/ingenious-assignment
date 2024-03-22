package tech.stonks.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.stonks.presentation.shared.model.*
import tech.stonks.ui.R

@Composable
fun StatusView(
    exception: PresentationException,
    onRetry: (() -> Unit)? = null,
) {
    StatusView(
        onRetry = onRetry,
        icon = {
            Icon(
                when (exception) {
                    is NetworkPresentationException -> painterResource(id = R.drawable.icon_no_network)
                    is TooManyRequestsPresentationException -> painterResource(id = R.drawable.icon_too_many_requests)
                    else -> painterResource(id = R.drawable.icon_error)
                },
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
        },
        title = stringResource(
            id = when (exception) {
                is NetworkPresentationException -> R.string.error_title_network
                is TooManyRequestsPresentationException -> R.string.error_title_too_many_requests
                is UnauthorizedPresentationException -> R.string.error_title_unauthorized
                is NotFoundPresentationException -> R.string.error_title_not_found
                else -> R.string.error_title_unknown
            }
        ),
        message = stringResource(
            id = when (exception) {
                is NetworkPresentationException -> R.string.error_message_network
                is TooManyRequestsPresentationException -> R.string.error_message_too_many_requests
                is UnauthorizedPresentationException -> R.string.error_message_unauthorized
                is NotFoundPresentationException -> R.string.error_message_not_found
                else -> R.string.error_message_unknown
            }
        ),
    )
}

@Composable
fun StatusView(
    icon: @Composable () -> Unit,
    title: String,
    message: String? = null,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center,
        )
        message?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
        onRetry?.let {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = it,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.error_retry),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
