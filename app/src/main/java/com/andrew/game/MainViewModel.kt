package com.andrew.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set
    var score by mutableIntStateOf(0)
        private set
    private var counter by mutableIntStateOf(0)

    init {
        getListImages()
    }

    fun onEvent(event: EventoMainScreen) {
        when (event) {
            is EventoMainScreen.ClickItem -> onItemClick(event.index, event.itemCard)
        }
    }

    private fun getListImages() {
        val listImages = (Sources.listImages + Sources.listImages).shuffled().toMutableList()
        state = UiState(itemCards = listImages)
    }

    private fun onItemClick(index: Int, itemCard: ItemCard) {
        val updateList: MutableList<ItemCard> = mutableListOf()
        state.itemCards.mapIndexed { ind, item ->
            updateList.add(
                if (index == ind) item.copy(
                    visible = !itemCard.visible,
                    color = if (item.color == Gray) Red else Gray
                ) else item
            )
        }
        state = UiState(itemCards = updateList)
        rotateCardsTwoShowed()
        checkMatch()
    }

    private fun rotateCardsTwoShowed() {
        val updateList: MutableList<ItemCard> = mutableListOf()
        var check = -1
        viewModelScope.launch {
            state.itemCards.map {
                if (it.visible && check != 0) counter += 1
                if (counter == 2) {
                    delay(1000)
                    state.itemCards.map { itemCard ->
                        updateList.add(itemCard.copy(visible = false, color = Red))
                        counter = 0
                        check = 0
                    }
                }
            }
            state = UiState(itemCards = updateList.ifEmpty { state.itemCards })
        }
    }

    private fun checkMatch() {
        val updateList: MutableList<ItemCard> = mutableListOf()
        var firstItem = String()
        var secondItem = String()
        var counter = 0
        state.itemCards.map { itemCard ->
            if (itemCard.visible) {
                if (firstItem.isEmpty()) firstItem = itemCard.title.toString()
                if (counter == 1) secondItem = itemCard.title.toString()
                counter += 1
            }
        }
        if (firstItem == secondItem) {
            score += 2
            state.itemCards.map { item ->
                updateList.add(item.copy(visible = item.visible))
            }
            state = UiState(itemCards = updateList)
        }
    }
}