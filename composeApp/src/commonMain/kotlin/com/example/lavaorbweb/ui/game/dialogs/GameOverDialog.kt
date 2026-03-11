package com.example.lavaorbweb.ui.game.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lavaorbweb.ui.theme.LavaOrbTheme
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.back
import lavaorbweb.composeapp.generated.resources.back_button
import lavaorbweb.composeapp.generated.resources.game_background
import lavaorbweb.composeapp.generated.resources.game_over
import lavaorbweb.composeapp.generated.resources.play_again
import lavaorbweb.composeapp.generated.resources.play_again_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameOverDialog(
    score: Int,
    onRestartClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.game_background),
                contentScale = ContentScale.Crop
            )
            .clickable(
                enabled = false,
                onClick = {}
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.TopStart)
                .safeDrawingPadding()
                .fillMaxWidth(0.22f)
                .aspectRatio(93f/75f),
            shape = RectangleShape,
            onClick = onExitClick
        ) {
            Image(
                modifier = Modifier.fillMaxSize(0.9f),
                painter = painterResource(Res.drawable.back_button),
                contentDescription = stringResource(Res.string.back),
                contentScale = ContentScale.FillWidth
            )
        }
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(Res.string.game_over),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .aspectRatio(227f/114f),
                shape = RectangleShape,
                onClick = onRestartClick
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(0.9f),
                    painter = painterResource(Res.drawable.play_again_button),
                    contentDescription = stringResource(Res.string.play_again),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun GameOverDialogPreview() {
    LavaOrbTheme {
        GameOverDialog(
            score = 123,
            onRestartClick = {},
            onExitClick = {}
        )
    }
}