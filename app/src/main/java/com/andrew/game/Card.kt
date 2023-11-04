package com.andrew.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@ExperimentalAnimationApi
@Composable
fun CardImage(
    index: Int,
    itemCard: ItemCard,
    viewModel: MainViewModel
) {
    val animatedColor by animateColorAsState(
        targetValue = if (!itemCard.visible) Color.Red else Color.Gray,
        label = ""
    )
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp)
            .graphicsLayer(
                rotationY = animateFloatAsState(if (!itemCard.visible) 180f else 0f, label = "").value,
                translationY = 0f
            )
            .clickable { viewModel.onEvent(
                EventoMainScreen.ClickItem(index, itemCard)
            ) },
        colors = CardDefaults.cardColors(
            containerColor = itemCard.color
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        AnimatedVisibility(visible = itemCard.visible, enter = fadeIn(), exit = fadeOut()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = itemCard.image,
                    contentDescription = stringResource(id = itemCard.title)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = stringResource(id = itemCard.title),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}