package tech.stonks.data.shared.model

abstract class DataException(exception: Exception?) : Exception(exception)

class NetworkDataException(exception: Exception?) : DataException(exception)

class UnauthorizedDataException(exception: Exception?) : DataException(exception)

class ForbiddenDataException(exception: Exception?) : DataException(exception)

class NotFoundDataException(exception: Exception?) : DataException(exception)

class UnknownDataException(exception: Exception?) : DataException(exception)

