package com.techullurgy.composeuisapplication.swiperefresh


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A class to encapsulate details of different indicator sizes.
 *
 * @param size The overall size of the indicator.
 * @param arcRadius The radius of the arc.
 * @param strokeWidth The width of the arc stroke.
 * @param arrowWidth The width of the arrow.
 * @param arrowHeight The height of the arrow.
 */
@Immutable
private data class SwipeRefreshIndicatorSizes(
    val size: Dp,
    val arcRadius: Dp,
    val strokeWidth: Dp,
    val arrowWidth: Dp,
    val arrowHeight: Dp,
)

/**
 * The default/normal size values for [SwipeRefreshIndicator].
 */
private val DefaultSizes = SwipeRefreshIndicatorSizes(
    size = 40.dp,
    arcRadius = 7.5.dp,
    strokeWidth = 2.5.dp,
    arrowWidth = 10.dp,
    arrowHeight = 5.dp,
)

/**
 * The 'large' size values for [SwipeRefreshIndicator].
 */
private val LargeSizes = SwipeRefreshIndicatorSizes(
    size = 56.dp,
    arcRadius = 11.dp,
    strokeWidth = 3.dp,
    arrowWidth = 12.dp,
    arrowHeight = 6.dp,
)

@Composable
fun SwipeRefreshIndicator(
    state: SwipeRefreshState,
    refreshTriggerDistance: Dp,
    modifier: Modifier = Modifier,
    fade: Boolean = true,
    scale: Boolean = false,
    arrowEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    refreshingOffset: Dp = 16.dp,
    largeIndication: Boolean = false,
    elevation: Dp = 6.dp,
) {
    val sizes = if (largeIndication) LargeSizes else DefaultSizes

    val indicatorRefreshTrigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    val indicatorHeight = with(LocalDensity.current) { sizes.size.roundToPx() }
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }

    val slingshot = rememberUpdatedSlingshot(
        offsetY = state.indicatorOffset,
        maxOffsetY = indicatorRefreshTrigger,
        height = indicatorHeight,
    )

    var offset by remember { mutableFloatStateOf(0f) }

    if (state.isSwipeInProgress) {
        // If the user is currently swiping, we use the 'slingshot' offset directly
        offset = slingshot.offset.toFloat()
    } else {
        // If there's no swipe currently in progress, animate to the correct resting position
        LaunchedEffect(state.isRefreshing) {
            animate(
                initialValue = offset,
                targetValue = when {
                    state.isRefreshing -> indicatorHeight + refreshingOffsetPx
                    else -> 0f
                }
            ) { value, _ ->
                offset = value
            }
        }
    }

    val adjustedElevation = when {
        state.isRefreshing -> elevation
        offset > 0.5f -> elevation
        else -> 0.dp
    }

    Surface(
        modifier = modifier
            .size(size = sizes.size)
            .graphicsLayer {
                // Translate the indicator according to the slingshot
                translationY = offset - indicatorHeight

                val scaleFraction = if (scale && !state.isRefreshing) {
                    val progress = offset / indicatorRefreshTrigger.coerceAtLeast(1f)

                    // We use LinearOutSlowInEasing to speed up the scale in
                    LinearOutSlowInEasing
                        .transform(progress)
                        .coerceIn(0f, 1f)
                } else 1f

                scaleX = scaleFraction
                scaleY = scaleFraction
            },
        shape = shape,
        color = backgroundColor,
        shadowElevation = adjustedElevation
    ) {
        val painter = remember { CircularProgressPainter() }
        painter.arcRadius = sizes.arcRadius
        painter.strokeWidth = sizes.strokeWidth
        painter.arrowWidth = sizes.arrowWidth
        painter.arrowHeight = sizes.arrowHeight
        painter.arrowEnabled = arrowEnabled && !state.isRefreshing
        painter.color = contentColor
        val alpha = if (fade) {
            (state.indicatorOffset / indicatorRefreshTrigger).coerceIn(0f, 1f)
        } else {
            1f
        }
        painter.alpha = alpha

        painter.startTrim = slingshot.startTrim
        painter.endTrim = slingshot.endTrim
        painter.rotation = slingshot.rotation
        painter.arrowScale = slingshot.arrowScale

        // This shows either an Image with CircularProgressPainter or a CircularProgressIndicator,
        // depending on refresh state
        Crossfade(
            targetState = state.isRefreshing,
            animationSpec = tween(durationMillis = CrossfadeDurationMs), label = ""
        ) { refreshing ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (refreshing) {
                    val circleSize = (sizes.arcRadius + sizes.strokeWidth) * 2
                    CircularProgressIndicator(
                        color = contentColor,
                        strokeWidth = sizes.strokeWidth,
                        modifier = Modifier.size(circleSize),
                    )
                } else {
                    Image(
                        painter = painter,
                        contentDescription = "Refreshing"
                    )
                }
            }
        }
    }
}

private const val CrossfadeDurationMs = 100