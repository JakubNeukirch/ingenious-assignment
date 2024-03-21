package tech.stonks.datasource.shared.mapper

import retrofit2.HttpException
import tech.stonks.data.shared.model.*
import java.net.SocketException
import java.net.SocketTimeoutException

class ExceptionDataMapper {
    fun map(exception: Exception): DataException {
        return when {
            exception is SocketException ||
                    exception is SocketTimeoutException -> NetworkDataException(exception)

            exception is HttpException && exception.code() == 401 -> UnauthorizedDataException(exception)
            exception is HttpException && exception.code() == 404 -> NotFoundDataException(exception)
            else -> UnknownDataException(exception)
        }
    }
}

suspend fun <T> ExceptionDataMapper.runWithExceptionMapping(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: Exception) {
        throw map(e)
    }
}
