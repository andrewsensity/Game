package com.andrew.game

sealed class EventoMainScreen {
    data class ClickItem(
        val index: Int,
        val itemCard: ItemCard
    ): EventoMainScreen()
}
