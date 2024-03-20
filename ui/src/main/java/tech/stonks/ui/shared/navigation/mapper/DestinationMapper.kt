package tech.stonks.ui.shared.navigation.mapper

import tech.stonks.presentation.shared.model.PresentationDestination
import tech.stonks.ui.shared.navigation.UiDestination

interface DestinationMapper {
    fun map(destination: PresentationDestination): UiDestination
}
