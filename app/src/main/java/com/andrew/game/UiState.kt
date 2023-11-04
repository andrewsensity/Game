package com.andrew.game

import androidx.compose.ui.graphics.Color

data class UiState(
    var loading: Boolean = false,
    val itemCards: MutableList<ItemCard> = mutableListOf(),
    val visible: Boolean = false,
    val color: Color = Color.Red
)
