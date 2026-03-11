package com.example.lavaorbweb.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lavaorbweb.ui.game.GameScreen
import com.example.lavaorbweb.ui.home.HomeScreen
import com.example.lavaorbweb.ui.score.ScoreScreen
import kotlinx.serialization.Serializable

@Serializable
object StartScreen
@Serializable
object GameScreen
@Serializable
object ScoreScreen

@Composable
fun GameNavigation() {
    val navController = rememberNavController()

    NavHost(
        enterTransition = {
            slideInHorizontally (
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally (
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally (
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally (
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        navController = navController,
        startDestination = StartScreen
    ) {
        composable<StartScreen> {
            HomeScreen (
                navigateGame = {
                    navController.navigateSafe(GameScreen)
                },
                navigateScore = {
                    navController.navigateSafe(ScoreScreen)
                }
            )
        }
        composable<GameScreen> {
            GameScreen (
                navigateHome = {
                    navController.popSafe()
                }
            )
        }
        composable<ScoreScreen> {
            ScoreScreen(
                navigateHome = {
                    navController.popSafe()
                }
            )
        }
    }
}

private fun NavController.navigateSafe(route: Any, builder: NavOptionsBuilder.() -> Unit = {}) {
    if (currentBackStackEntry?.lifecycle?.currentState == (Lifecycle.State.RESUMED)) {
        navigate(route, builder)
    }
}

private fun NavController.popSafe() {
    if (currentBackStackEntry?.lifecycle?.currentState == (Lifecycle.State.RESUMED)) {
        popBackStack()
    }
}