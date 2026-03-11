package com.example.lavaorbweb.ui.score

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lavaorbweb.model.GameRecord
import kotlin.time.Instant
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.back
import lavaorbweb.composeapp.generated.resources.back_button
import lavaorbweb.composeapp.generated.resources.game_background
import lavaorbweb.composeapp.generated.resources.record_item
import lavaorbweb.composeapp.generated.resources.records
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScoreScreen(
    navigateHome: () -> Unit,
    viewModel: ScoreViewModel = koinViewModel()
) {
    val results = viewModel.results.collectAsState()

    ScoreScreenContent(
        navigateToStartScreen = navigateHome,
        results = results.value
    )
}

@Composable
private fun ScoreScreenContent(
    navigateToStartScreen: () -> Unit,
    results: List<GameRecord>
) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(Res.drawable.game_background),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    Box (
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) {
        IconButton(
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.TopStart)
                .safeDrawingPadding()
                .fillMaxWidth(0.22f)
                .aspectRatio(93f/75f),
            shape = RectangleShape,
            onClick = navigateToStartScreen
        ) {
            Image(
                modifier = Modifier.fillMaxSize(0.9f),
                painter = painterResource(Res.drawable.back_button),
                contentDescription = stringResource(Res.string.back),
                contentScale = ContentScale.FillWidth
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .safeDrawingPadding(),
            text = stringResource(Res.string.records),
            style = MaterialTheme.typography.titleMedium
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(results) { result ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(247f/55f),
                            painter = painterResource(Res.drawable.record_item),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Row (
                            modifier = Modifier.fillMaxWidth(0.8f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = result.timestamp.toDateString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = result.score.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

private fun Long.toDateString(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = localDateTime.day.toString().padStart(2, '0')
    val month = localDateTime.month.number.toString().padStart(2, '0')

    return "$day.$month"
}

@Preview()
@Composable
fun ScoreScreenMediumPreview() {
    MaterialTheme {
        ScoreScreenContent(
            navigateToStartScreen = {},
            results = listOf(
                GameRecord(1, 132),
                GameRecord(2, 333),
                GameRecord(3, 321),
                GameRecord(4, 645),
                GameRecord(3, 321),
                GameRecord(4, 645),
            )
        )
    }
}