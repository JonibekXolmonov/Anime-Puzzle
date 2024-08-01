package com.animeworld.puzzlecore.util

import android.content.Context
import android.content.ContextWrapper
import android.media.AudioManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.animeworld.puzzlecore.data.model.GamePiece
import com.animeworld.puzzlecore.data.model.PieceInBoard
import com.animeworld.puzzlecore.data.model.PieceInOffer
import kotlin.math.roundToInt

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> throw IllegalArgumentException()
}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 1f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = androidx.compose.ui.graphics.Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

fun List<ImageBitmap>.toGamePiece() = this.map {
    GamePiece(it)
}

fun List<GamePiece>.toPieceInOffer() = this.map {
    PieceInOffer(it.bitmap, it.id)
}

fun List<GamePiece>.toPieceInBoard() = this.map {
    PieceInBoard(it.bitmap, it.id)
}

fun PieceInOffer.toPieceInBoard() = PieceInBoard(bitmap, id)

@Stable
fun Offset.round(): IntOffset = IntOffset(x.roundToInt(), y.roundToInt())
