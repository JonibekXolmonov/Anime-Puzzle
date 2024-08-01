package com.animeworld.puzzlecore.ui.screen

import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.animeworld.puzzlecore.MainActivity
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.data.jigsaw.DragTarget
import com.animeworld.puzzlecore.data.jigsaw.DropTarget
import com.animeworld.puzzlecore.data.jigsaw.LongPressDraggable
import com.animeworld.puzzlecore.data.model.PieceInBoard
import com.animeworld.puzzlecore.data.model.PieceInOffer
import com.animeworld.puzzlecore.ui.theme.MainRed
import com.animeworld.puzzlecore.ui.theme.MainWhite
import com.animeworld.puzzlecore.ui.viewmodel.GamaUiState
import com.animeworld.puzzlecore.ui.viewmodel.GameViewModel
import com.animeworld.puzzlecore.ui.viewmodel.GameViewModelFactory
import com.animeworld.puzzlecore.util.advancedShadow
import com.animeworld.puzzlecore.util.getActivity
import com.animeworld.puzzlecore.util.round
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.log

@Destination
@Composable
fun GameScreen(
    navigator: DestinationsNavigator,
    viewModel: GameViewModel = viewModel(factory = GameViewModelFactory())
) {

    val context = LocalContext.current
    val activity = context.getActivity() as MainActivity
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.image_2
    )

    LaunchedEffect(Unit) {
        viewModel.split(bitmap)
    }

    GameBackground {
        LongPressDraggable(Modifier.fillMaxSize()) {
            GameContent(uiState, onPieceDrop = {
//                viewModel.onPieceDrop(it)
            })
        }
    }

}

@Composable
fun GameContent(
    uiState: GamaUiState,
    onRestart: () -> Unit = {},
    onNext: () -> Unit = {},
    onPieceDrop: (PieceInOffer) -> Unit
) {
    val paddingValues = PaddingValues(start = 8.dp, end = 20.dp, top = 8.dp, bottom = 30.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        JigSawPieces(uiState, onRestart, onNext)
        PuzzleBoard(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            piecesInBoard = uiState.piecesCollectedInBoard,
            onPieceDrop = onPieceDrop
        )
    }
}

@Composable
fun PuzzleBoard(
    modifier: Modifier,
    piecesInBoard: List<PieceInBoard>,
    onPieceDrop: (PieceInOffer) -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MainRed)
            .padding(8.dp)
    ) {
        DropTarget<PieceInOffer>(modifier = Modifier) { isInBound, data: PieceInOffer? ->
            Box(modifier = modifier)
            if (isInBound) {
                data?.let {
                    onPieceDrop(it)
                    Canvas(
                        modifier = Modifier
                            .size(81.dp)
                    ) {
                        drawImage(it.bitmap)
                    }
                }
            }
        }
    }
}

@Composable
fun JigSawPieces(
    uiState: GamaUiState,
    onRestart: () -> Unit,
    onNext: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ActionButton(onRestart, onNext)
        JigSawPieceSelector(
            modifier =
            Modifier
                .weight(1f),
            uiState.fourPiecesOffer
        )
    }
}

@Composable
fun JigSawPieceSelector(
    modifier: Modifier,
    pieces: List<PieceInOffer>
) {
    BoxWithConstraints(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .width(184.dp)
            .advancedShadow(
                alpha = 0.6f,
                cornersRadius = 3.dp,
                shadowBlurRadius = 2.dp,
                offsetY = 3.dp
            )
            .background(MainRed.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        if (pieces.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    pieces.take(2).forEach { piece ->
                        PieceImage(piece = piece, pieceSize = 81.dp)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    pieces.takeLast(2).forEach { piece ->
                        PieceImage(piece = piece, pieceSize = 81.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun PieceImage(
    modifier: Modifier = Modifier,
    piece: PieceInOffer,
    pieceSize: Dp
) {
    DragTarget(
        modifier = modifier.size(pieceSize), dataToDrop = piece
    ) {
        Image(
            bitmap = piece.bitmap,
            contentDescription = null,
            modifier = Modifier
                .size(pieceSize)
        )
    }
}

@Composable
fun ActionButton(
    onRestart: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        IconButton(onClick = onRestart, modifier = Modifier.size(79.dp, 85.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_reset), contentDescription = null,
                modifier = Modifier.size(79.dp, 85.dp)
            )
        }

        IconButton(onClick = onNext, modifier = Modifier.size(120.dp, 48.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_next), contentDescription = null,
                modifier = Modifier.size(100.dp, 48.dp)
            )
        }
    }
}

@Composable
fun GameBackground(
    @DrawableRes backgroundImageRes: Int = R.drawable.background,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = backgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        content()
    }
}

/*
LazyVerticalGrid(
        columns = GridCells.Fixed(10),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MainRed)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(piecesInBoard) { index: Int, piece: PieceInBoard ->
            DropTarget<PieceInOffer>(modifier = Modifier) { isInBound, data: PieceInOffer?, dragPosition: Offset, dragOffset: Offset ->
                val bgColor = if (isInBound) {
                    Color.Black.copy(alpha = 0.5f)
                } else {
                    Color.Transparent
                }
//                if (isInBound) {
                    piece.bitmap?.let {
//                        onPieceDrop(it, index)
                        Canvas(modifier = Modifier.size(81.dp)){
                            drawImage(it)
                        }
                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .background(bgColor)
//                        .size(81.dp)
//                ) {
//                    if (piece.bitmap != null) {
//                        Image(bitmap = piece.bitmap, contentDescription = null)
//                    }
//                }
            }
        }
    }
 */