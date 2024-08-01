package com.animeworld.puzzlecore.data.model

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

data class PieceInBoard(
    val bitmap: ImageBitmap?,
    val id: UUID
)

val EMPTY_PIECE_IN_BOARD = PieceInBoard(
    bitmap = null,
    id = UUID.randomUUID()
)

val INITIAL_LIST_IN_BOARD = List(50){
    EMPTY_PIECE_IN_BOARD
}