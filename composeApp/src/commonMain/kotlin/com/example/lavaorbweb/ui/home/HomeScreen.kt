package com.example.lavaorbweb.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.app_name
import lavaorbweb.composeapp.generated.resources.game_title
import lavaorbweb.composeapp.generated.resources.home_background
import lavaorbweb.composeapp.generated.resources.play
import lavaorbweb.composeapp.generated.resources.play_button
import lavaorbweb.composeapp.generated.resources.privacy_policy
import lavaorbweb.composeapp.generated.resources.privacy_policy_button
import lavaorbweb.composeapp.generated.resources.records
import lavaorbweb.composeapp.generated.resources.records_button
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    navigateGame: () -> Unit,
    navigateScore: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.home_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.fillMaxWidth(0.53f),
                painter = painterResource(Res.drawable.game_title),
                contentDescription = stringResource(Res.string.app_name),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.weight(0.01f))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(0.54f)
                    .aspectRatio(227f/114f),
                shape = RectangleShape,
                onClick = navigateGame
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    painter = painterResource(Res.drawable.play_button),
                    contentDescription = stringResource(Res.string.play),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(0.01f))
            IconButton(
                modifier = Modifier
                    .fillMaxWidth(0.47f)
                    .aspectRatio(192f/98f),
                shape = RectangleShape,
                onClick = navigateScore
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    painter = painterResource(Res.drawable.records_button),
                    contentDescription = stringResource(Res.string.records),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(0.4f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StartScreenPreview() {
    HomeScreen(
        navigateGame = {},
        navigateScore = {},
    )
}