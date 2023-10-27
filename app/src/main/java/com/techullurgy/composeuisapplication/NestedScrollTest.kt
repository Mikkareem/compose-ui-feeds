package com.techullurgy.composeuisapplication

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberCustomSwipeRefreshState(isRefreshing: Boolean): CustomSwipeRefreshState = remember {
    CustomSwipeRefreshState(isRefreshing = isRefreshing).apply { this.isRefreshing = isRefreshing }
}

class CustomSwipeRefreshState(
    isRefreshing: Boolean
) {
    private val _indicatorOffset = Animatable(0f)
    private val mutatorMutex = MutatorMutex()

    var isRefreshing: Boolean by mutableStateOf(isRefreshing)

    var isSwipeInProgress: Boolean by mutableStateOf(false)

    val indicatorOffset: Float get() = _indicatorOffset.value

    internal suspend fun animateOffsetTo(offset: Float) {
        mutatorMutex.mutate {
            _indicatorOffset.animateTo(offset)
        }
    }

    internal suspend fun dispatchScrollDelta(delta: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            _indicatorOffset.snapTo(_indicatorOffset.value + delta)
        }
    }
}

class CustomSwipeRefresh