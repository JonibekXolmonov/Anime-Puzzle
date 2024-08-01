package com.animeworld.puzzlecore.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.animeworld.puzzlecore.MainActivity
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.ui.components.AppText
import com.animeworld.puzzlecore.ui.screen.destinations.GameScreenDestination
import com.animeworld.puzzlecore.ui.screen.destinations.PreviewScreenDestination
import com.animeworld.puzzlecore.ui.screen.destinations.SettingsScreenDestination
import com.animeworld.puzzlecore.util.getActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun MenuScreen(
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current
    val activity = context.getActivity() as MainActivity

    Background(
        content = {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(top = 80.dp, end = 20.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                AppText(
                    modifier = Modifier.padding(end = 64.dp),
                    textResId = R.string.play,
                    style = MaterialTheme.typography.titleMedium,
                    onClick = {
                        navigator.navigate(GameScreenDestination)
                        activity.clickSound()
                    })
                AppText(
                    textResId = R.string.final_piece,
                    style = MaterialTheme.typography.titleSmall,
                    onClick = {
//                        navigator.navigate()
                        activity.clickSound()
                    })
                AppText(
                    modifier = Modifier.padding(end = 192.dp),
                    textResId = R.string.preview,
                    style = MaterialTheme.typography.titleSmall,
                    onClick = {
                        navigator.navigate(PreviewScreenDestination)
                        activity.clickSound()
                    })
            }
        },
        contentAlignment = Alignment.CenterEnd,
        iconAlignment = Alignment.TopEnd,
        iconSize = DpSize(64.dp, 64.dp),
        onClick = {
            navigator.navigate(SettingsScreenDestination)
            activity.clickSound()
        }
    )
}

@Composable
fun Background(
    content: @Composable () -> Unit,
    contentAlignment: Alignment,
    iconAlignment: Alignment,
    onClick: () -> Unit,
    iconSize: DpSize,
    @DrawableRes iconResourceId: Int = R.drawable.ic_settings,
    @DrawableRes backgroundImageRes: Int = R.drawable.menu_background
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {

        Image(
            painter = painterResource(id = backgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(8.dp)
                .align(iconAlignment)
                .size(iconSize),
        ) {
            Image(
                painter = painterResource(id = iconResourceId),
                contentDescription = null,
            )
        }

        content()
    }
}