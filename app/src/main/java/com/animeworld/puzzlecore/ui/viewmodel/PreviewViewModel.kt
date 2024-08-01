package com.animeworld.puzzlecore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.data.model.PreviewItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PreviewUiState(
    val items: List<PreviewItem> = emptyList()
)

class PreviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState get() = _uiState.asStateFlow()

    private fun setItems() {
        _uiState.update {
            it.copy(
                items = ITEMS
            )
        }
    }

    init {
        setItems()
    }
}

@Suppress("UNCHECKED_CAST")
class PreviewViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PreviewViewModel() as T
    }
}

val ITEMS = listOf(
    PreviewItem(R.drawable.image_1),
    PreviewItem(R.drawable.image_2),
    PreviewItem(R.drawable.image_3),
    PreviewItem(R.drawable.image_4),
    PreviewItem(R.drawable.image_5),
    PreviewItem(R.drawable.image_6),
    PreviewItem(R.drawable.image_7),
)