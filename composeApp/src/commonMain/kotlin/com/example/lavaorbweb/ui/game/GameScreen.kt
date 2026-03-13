package com.example.lavaorbweb.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.example.lavaorbweb.ui.game.dialogs.GameOverDialog
import com.example.lavaorbweb.ui.game.dialogs.PauseDialog
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.dot
import lavaorbweb.composeapp.generated.resources.game_background
import lavaorbweb.composeapp.generated.resources.lava_orb
import lavaorbweb.composeapp.generated.resources.pause
import lavaorbweb.composeapp.generated.resources.pause_button
import lavaorbweb.composeapp.generated.resources.ring
import lavaorbweb.composeapp.generated.resources.rope
import lavaorbweb.composeapp.generated.resources.score_container
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = koinViewModel(),
    navigateHome: () -> Unit,
) {
    var isPaused by remember { mutableStateOf(false) }
    val ringImage = imageResource(Res.drawable.ring)
    val lavaOrbImage = imageResource(Res.drawable.lava_orb)
    val dotImage = imageResource(Res.drawable.dot)
    val ropeImage = imageResource(Res.drawable.rope)

    val orb by viewModel.orb.collectAsState()
    val isGameInitialized by viewModel.isGameInitialized.collectAsState()
    val isFalling by viewModel.isFalling.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val score by viewModel.score.collectAsState()

    LaunchedEffect(isPaused, isGameOver) {
        while (!isPaused && !isGameOver) {
            withFrameNanos {
                viewModel.frame()
            }
        }
    }

    LaunchedEffect(isGameOver) {
        if (isGameOver && score > 0) {
            viewModel.saveResult()
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        if (!isGameOver) {
            isPaused = true
        }
    }

    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        isBackEnabled = true,
        onBackCompleted = {
            when {
                isGameOver -> navigateHome()
                isPaused -> isPaused = false
                else -> isPaused = true
            }
        }
    )

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(Res.drawable.game_background),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        val isLandscape = maxWidth > maxHeight
        val pauseWidthFraction = if (isLandscape) 0.14f else 0.22f
        val scoreWidthFraction = if (isLandscape) 0.14f else 0.21f

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (isGameInitialized) {
                            viewModel.dropOrb()
                        }
                    }
                }
                .onSizeChanged {
                    viewModel.initGame(Size(it.width.toFloat(), it.height.toFloat()), lavaOrbImage, ringImage, dotImage, ropeImage)
                }
        ) {
            if (!isFalling) {
                val ropeCenterX = viewModel.ropePosition.x + viewModel.ropeSize.width / 2f
                val ropeCenterY = viewModel.ropePosition.y

                rotate(
                    degrees = orb.rotation,
                    pivot = Offset(ropeCenterX, ropeCenterY)
                ) {
                    drawImage(
                        image = ropeImage,
                        dstOffset = IntOffset(viewModel.ropePosition.x.toInt(), viewModel.ropePosition.y.toInt()),
                        dstSize = IntSize(viewModel.ropeSize.width.toInt(), viewModel.ropeSize.height.toInt())
                    )
                    drawImage(
                        image = orb.bitmap,
                        dstOffset = IntOffset(
                            x = orb.x.toInt(),
                            y = orb.y.toInt()
                        ),
                        dstSize = IntSize(viewModel.orbSize.width.toInt(), viewModel.orbSize.height.toInt())
                    )
                }
            } else {
                drawImage(
                    image = orb.bitmap,
                    dstOffset = IntOffset(orb.x.toInt(), orb.y.toInt()),
                    dstSize = IntSize(viewModel.orbSize.width.toInt(), viewModel.orbSize.height.toInt())
                )
            }

            drawImage(
                image = dotImage,
                srcSize = IntSize(dotImage.width, dotImage.height),
                srcOffset = IntOffset(0, 0),
                dstOffset = IntOffset(
                    x = (viewModel.pivotingPosition.x - viewModel.dotSize.width / 2f).toInt(),
                    y = (viewModel.pivotingPosition.y - viewModel.dotSize.height / 2f).toInt()
                ),
                dstSize = IntSize(viewModel.dotSize.width.toInt(), viewModel.dotSize.height.toInt()),
            )

            drawImage(
                image = ringImage,
                dstOffset = IntOffset(viewModel.ringPosition.x.toInt(), viewModel.ringPosition.y.toInt()),
                dstSize = IntSize(viewModel.ringSize.width.toInt(), viewModel.ringSize.height.toInt())
            )
        }
        Row (
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth(pauseWidthFraction)
                    .aspectRatio(93f/75f),
                shape = RectangleShape,
                onClick = {
                    isPaused = true
                }
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(0.9f),
                    painter = painterResource(Res.drawable.pause_button),
                    contentDescription = stringResource(Res.string.pause),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.padding(end = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(scoreWidthFraction),
                    painter = painterResource(Res.drawable.score_container),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    if (isPaused && !isGameOver) {
        PauseDialog(
            onResume = {
                isPaused = false
            },
            onExit = {
                navigateHome()
                isPaused = false
            }
        )
    }
    if (isGameOver) {
        GameOverDialog(
            score = score,
            onExitClick = {
                viewModel.resetGame()
                isPaused = false
                navigateHome()
            },
            onRestartClick = {
                isPaused = false
                viewModel.resetGame()
            }
        )
    }
}
