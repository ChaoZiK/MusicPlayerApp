package com.example.musicplayer.ui.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.compose.runtime.State

@Composable
fun animateProgress(
    targetValue: Float,
    durationMillis: Int = 100,
    easing: Easing = Easing { it }
): State<Float> {
    return animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = easing::transform
        ),
        label = ""
    )
}

object Transitions {
    val fadeIn = fadeIn(animationSpec = tween(300))
    val fadeOut = fadeOut(animationSpec = tween(300))

    object Navigation {
        val defaultEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
            fadeIn(animationSpec = tween(300))
        }

        val defaultExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
            fadeOut(animationSpec = tween(300))
        }

        val fullPlayerEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }

        val fullPlayerExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    }
}