package tech.stonks.presentation.shared.model

abstract class PresentationException(exception: Exception?) : Exception(exception)

class NetworkPresentationException(exception: Exception?) : PresentationException(exception)

class UnauthorizedPresentationException(exception: Exception?) : PresentationException(exception)

class TooManyRequestsPresentationException(exception: Exception?) : PresentationException(exception)

class NotFoundPresentationException(exception: Exception?) : PresentationException(exception)

class UnknownPresentationException(exception: Exception?) : PresentationException(exception)
