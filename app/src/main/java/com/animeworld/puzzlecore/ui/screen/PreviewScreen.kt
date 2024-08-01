package com.animeworld.puzzlecore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.animeworld.puzzlecore.MainActivity
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.data.model.PreviewItem
import com.animeworld.puzzlecore.ui.theme.MainWhite
import com.animeworld.puzzlecore.ui.theme.RammettoOneFamily
import com.animeworld.puzzlecore.ui.viewmodel.PreviewViewModel
import com.animeworld.puzzlecore.ui.viewmodel.PreviewViewModelFactory
import com.animeworld.puzzlecore.util.getActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PreviewScreen(
    navigator: DestinationsNavigator,
    viewModel: PreviewViewModel = viewModel(factory = PreviewViewModelFactory())
) {

    val context = LocalContext.current
    val activity = context.getActivity() as MainActivity
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Background(
        content = {
            PreviewContent(
                uiState.items
            )
        },
        contentAlignment = Alignment.Center,
        iconAlignment = Alignment.TopStart,
        backgroundImageRes = R.drawable.background,
        iconResourceId = R.drawable.ic_back,
        iconSize = DpSize(140.dp, 57.dp),
        onClick = {
            navigator.navigateUp()
            activity.clickSound()
        }
    )
}

@Composable
fun PreviewContent(items: List<PreviewItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            (0..3).forEach {
                PreviewItem(item = items[it])
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            (4..6).forEach {
                PreviewItem(item = items[it])
            }
        }
    }
}

@Composable
fun PreviewItem(item: PreviewItem) {
    Box(
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = item.imageRes), contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(152.dp, 85.dp)
                .blur(3.dp)
        )

        Text(
            text = item.percentage.toString().plus("%"),
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    colors = listOf(MainWhite, MainWhite),
                    tileMode = TileMode.Mirror
                )
            ),
            fontSize = 32.sp,
            fontFamily = RammettoOneFamily,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            lineHeight = 55.sp,
        )

        Text(
            text = item.percentage.toString().plus("%"),
            style = MaterialTheme.typography.bodySmall
        )
    }
}