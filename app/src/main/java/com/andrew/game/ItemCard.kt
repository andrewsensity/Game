package com.andrew.game

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class ItemCard(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    val visible: Boolean = false,
    val color: Color = Color.Red
)
