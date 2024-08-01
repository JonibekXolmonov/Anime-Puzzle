package com.animeworld.puzzlecore.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.animeworld.puzzlecore.MainActivity
import com.animeworld.puzzlecore.R
import com.animeworld.puzzlecore.ui.components.AppText
import com.animeworld.puzzlecore.ui.components.CustomSwitch
import com.animeworld.puzzlecore.ui.viewmodel.SettingsUiState
import com.animeworld.puzzlecore.ui.viewmodel.SettingsViewModel
import com.animeworld.puzzlecore.ui.viewmodel.SettingsViewModelFactory
import com.animeworld.puzzlecore.util.getActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    context: Context = LocalContext.current,
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            context
        )
    )
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = context.getActivity() as MainActivity

    LaunchedEffect(key1 = uiState) {
        if (uiState.isSoundEnabled) {
            activity.startBackgroundMusicService()
        } else {
            activity.stopBackgroundMusicService()
        }
    }

    Background(
        content = {
            SettingsContent(
                uiState = uiState,
                onSoundClick = {
                    viewModel.toggleSound()
                    activity.clickSound()
                },
                onMusicClick = {
                    viewModel.toggleMusic()
                    activity.clickSound()
                }
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
fun SettingsContent(
    uiState: SettingsUiState,
    onSoundClick: () -> Unit,
    onMusicClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(64.dp)
    ) {

        SettingsButton(
            onClick = onSoundClick,
            textResourceId = R.string.sound,
            isEnabled = uiState.isSoundEnabled
        )

        SettingsButton(
            onClick = onMusicClick,
            textResourceId = R.string.music,
            isEnabled = uiState.isMusicEnabled
        )
    }
}

@Composable
fun SettingsButton(
    onClick: () -> Unit,
    textResourceId: Int,
    isEnabled: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppText(textResId = textResourceId, style = MaterialTheme.typography.titleMedium)

        CustomSwitch(switchOn = isEnabled, onChange = onClick)
    }
}

@Composable
fun ThumbOn(modifier: Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.thumb_on_outer), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.thumb_on_inner), contentDescription = null)
    }
}

@Composable
fun ThumbOff(modifier: Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.thumb_off_outer), contentDescription = null)
        Image(painter = painterResource(id = R.drawable.thumb_off_inner), contentDescription = null)
    }
}