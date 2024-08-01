package com.animeworld.puzzlecore.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.animeworld.puzzlecore.ui.screen.ThumbOff
import com.animeworld.puzzlecore.ui.screen.ThumbOn
import com.animeworld.puzzlecore.ui.theme.MainRed
import com.animeworld.puzzlecore.ui.theme.MainWhite

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int,
    style: TextStyle,
    onClick: () -> Unit = {}
) {
    val style2 = style.copy(color = MainRed)
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy((-30).dp)
    ) {
        Text(
            text = stringResource(id = textResId),
            style = style
        )
        Text(
            text = stringResource(id = textResId),
            style = style2,
            modifier = Modifier
                .padding(end = 40.dp)
                .graphicsLayer {
                    rotationX = 180f
                }
        )
    }
}


@Composable
fun CustomSwitch(
    width: Dp = 260.dp,
    height: Dp = 104.dp,
    checkedTrackColor: Color = MainWhite,
    uncheckedTrackColor: Color = MainRed,
    gapBetweenThumbAndTrackEdge: Dp = 8.dp,
    iconInnerPadding: Dp = 4.dp,
    switchOn: Boolean,
    onChange: () -> Unit
) {

    // this is to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // for moving the thumb
    val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f)

    // outer rectangle with border
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(50))
            .background(if (switchOn) checkedTrackColor else uncheckedTrackColor)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onChange()
            },
        contentAlignment = Alignment.Center
    ) {

        // this is to add padding at the each horizontal side
        Box(
            modifier = Modifier
                .padding(
                    start = gapBetweenThumbAndTrackEdge,
                    end = gapBetweenThumbAndTrackEdge
                )
                .fillMaxSize(),
            contentAlignment = alignment
        ) {

            if (switchOn) {
                ThumbOn(Modifier.padding(iconInnerPadding))
            } else {
                ThumbOff(Modifier.padding(iconInnerPadding))
            }
        }
    }

    // gap between switch and the text
    Spacer(modifier = Modifier.height(height = 16.dp))

    Text(text = if (switchOn) "ON" else "OFF")
}

@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue, label = "")
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}