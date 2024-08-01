package com.animeworld.puzzlecore.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.animeworld.puzzlecore.R

val RammettoOneFamily = FontFamily(
    listOf(
        Font(R.font.rammetto_one_regular),
        Font(R.font.rammetto_one_regular, weight = FontWeight.Medium),
        Font(R.font.rammetto_one_regular, weight = FontWeight.SemiBold)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = RammettoOneFamily,
        fontWeight = FontWeight.W400,
        fontSize = 64.sp,
        textAlign = TextAlign.Center,
        color = MainWhite,
        lineHeight = 108.sp
    ),
    titleMedium = TextStyle(
        fontFamily = RammettoOneFamily,
        fontWeight = FontWeight.W400,
        fontSize = 48.sp,
        textAlign = TextAlign.Center,
        color = MainWhite,
        lineHeight = 82.sp
    ),
    titleSmall = TextStyle(
        fontFamily = RammettoOneFamily,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        color = MainWhite,
        lineHeight = 55.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RammettoOneFamily,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        color = MainRed,
        lineHeight = 55.sp,
        drawStyle = Stroke(
            miter = 10f,
            width = 5f,
            join = StrokeJoin.Round
        )
    ),
)
