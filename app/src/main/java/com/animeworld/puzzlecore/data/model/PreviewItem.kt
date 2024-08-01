package com.animeworld.puzzlecore.data.model

import androidx.annotation.DrawableRes

data class PreviewItem(
    @DrawableRes val imageRes: Int,
    val percentage: Int = 99
)