package com.animeworld.puzzlecore.data.model

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

data class PieceInOffer(
    val bitmap: ImageBitmap,
    val id: UUID
)