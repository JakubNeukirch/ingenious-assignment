package tech.stonks.data.shared.mapper

import tech.stonks.data.shared.model.ForbiddenDataException
import tech.stonks.data.shared.model.NetworkDataException
import tech.stonks.data.shared.model.NotFoundDataException
import tech.stonks.data.shared.model.UnauthorizedDataException
import tech.stonks.presentation.shared.model.*

class ExceptionPresentationMapper {
    fun map(exception: Exception): PresentationException {
        return when (exception) {
            is NetworkDataException -> NetworkPresentationException(exception)
            is UnauthorizedDataException -> UnauthorizedPresentationException(exception)
            is ForbiddenDataException -> TooManyRequestsPresentationException(exception)
            is NotFoundDataException -> NotFoundPresentationException(exception)
            else -> UnknownPresentationException(exception)
        }
    }
}

suspend fun <T> ExceptionPresentationMapper.runWithExceptionMapping(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw map(e)
    }
}
