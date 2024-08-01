package com.animeworld.puzzlecore.data.model

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

data class GamePiece(
    val bitmap: ImageBitmap,
    val id: UUID = UUID.randomUUID()
)