package com.example.lavaorbweb.ui.game.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.lavaorbweb.ui.theme.LavaOrbTheme
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.exit
import lavaorbweb.composeapp.generated.resources.exit_button
import lavaorbweb.composeapp.generated.resources.game_background
import lavaorbweb.composeapp.generated.resources.pause
import lavaorbweb.composeapp.generated.resources.resume
import lavaorbweb.composeapp.generated.resources.resume_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PauseDialog(
    onResume: () -> Unit,
    onExit: () -> Unit
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
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(Res.string.pause),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .aspectRatio(227f/114f),
                shape = RectangleShape,
                onClick = onResume
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(0.9f),
                    painter = painterResource(Res.drawable.resume_button),
                    contentDescription = stringResource(Res.string.resume),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(0.46f)
                    .aspectRatio(192f/98f),
                shape = RectangleShape,
                onClick = onExit
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(0.9f),
                    painter = painterResource(Res.drawable.exit_button),
                    contentDescription = stringResource(Res.string.exit),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun PauseDialogPreview() {
    LavaOrbTheme {
        PauseDialog(onResume = {}, onExit = {})
    }
}