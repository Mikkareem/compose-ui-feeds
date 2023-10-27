package com.techullurgy.composeuisapplication.swiperefresh

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SwipeRefresh(
    state: SwipeRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    swipeEnabled: Boolean = true,
    refreshTriggerDistance: Dp = 80.dp,
    indicatorAlignment: Alignment = Alignment.TopCenter,
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    indicator: @Composable (state: SwipeRefreshState, refreshTrigger: Dp) -> Unit = { s, trigger ->
        SwipeRefreshIndicator(s, trigger)
    },
    clipIndicatorToPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)

    // Our LaunchedEffect, which animates the indicator to its resting position
    LaunchedEffect(state.isSwipeInProgress) {
        if (!state.isSwipeInProgress) {
            // If there's not a swipe in progress, rest the indicator at 0f
            state.animateOffsetTo(0f)
        }
    }

    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    // Our nested scroll connection, which updates our state.
    val nestedScrollConnection = remember(state, coroutineScope) {
        SwipeRefreshNestedScrollConnection(state, coroutineScope) {
            // On refresh, re-dispatch to the update onRefresh block
            updatedOnRefresh.value.invoke()
        }
    }.apply {
        this.enabled = swipeEnabled
        this.refreshTrigger = refreshTriggerPx
    }

    Box(modifier.nestedScroll(connection = nestedScrollConnection)) {
        content()

        Box(
            Modifier
                // If we're not clipping to the padding, we use clipToBounds() before the padding()
                // modifier.
                .let { if (!clipIndicatorToPadding) it.clipToBounds() else it }
                .padding(indicatorPadding)
                .matchParentSize()
                // Else, if we're are clipping to the padding, we use clipToBounds() after
                // the padding() modifier.
                .let { if (clipIndicatorToPadding) it.clipToBounds() else it }
        ) {
            Box(Modifier.align(indicatorAlignment)) {
                indicator(state, refreshTriggerDistance)
            }
        }
    }
}