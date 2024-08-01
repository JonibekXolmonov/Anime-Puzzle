package com.animeworld.puzzlecore.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animeworld.puzzlecore.data.jigsaw.ImageSplit
import com.animeworld.puzzlecore.data.model.GamePiece
import com.animeworld.puzzlecore.data.model.INITIAL_LIST_IN_BOARD
import com.animeworld.puzzlecore.data.model.PieceInBoard
import com.animeworld.puzzlecore.data.model.PieceInOffer
import com.animeworld.puzzlecore.util.toGamePiece
import com.animeworld.puzzlecore.util.toPieceInBoard
import com.animeworld.puzzlecore.util.toPieceInOffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class GamaUiState(
    val allPuzzlePieces: List<GamePiece> = emptyList(),
    val fourPiecesOffer: List<PieceInOffer> = emptyList(),
    val piecesCollectedInBoard: List<PieceInBoard> = INITIAL_LIST_IN_BOARD,
    val isPuzzleSolved: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GamaUiState())
    val uiState get() = _uiState.asStateFlow()

    fun split(bitmap: Bitmap) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val splitImagePieces = ImageSplit.splitImage(bitmap).map {
                    it.asImageBitmap()
                }
//                    .shuffled()
                    .toGamePiece()

                _uiState.update {
                    it.copy(
                        allPuzzlePieces = splitImagePieces,
                        fourPiecesOffer = placeFourPiecesInOffer(splitImagePieces),
                        piecesCollectedInBoard = splitImagePieces.toPieceInBoard()
                    )
                }
            }
        }
    }

    private fun placeFourPiecesInOffer(splitImagePieces: List<GamePiece>): List<PieceInOffer> {
        return splitImagePieces.take(4).toPieceInOffer()
    }

    fun onPieceDrop(pieceInOffer: PieceInOffer, index: Int) {
        val piecesCollectedInBoard = _uiState.value.piecesCollectedInBoard.toMutableList()
        piecesCollectedInBoard[index] = pieceInOffer.toPieceInBoard()

        _uiState.update {
            it.copy(
                piecesCollectedInBoard = piecesCollectedInBoard
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel() as T
    }
}