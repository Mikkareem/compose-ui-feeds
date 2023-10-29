package com.techullurgy.composeuisapplication.graphicslayertest

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.RenderEffect as ComposeRenderEffect


@Preview(apiLevel = 33, device = "id:pixel_4a", showBackground = true, backgroundColor = 0xFF3A2F6E)
@Composable
private fun MainScreen() {
    var isMenuExtended by remember { mutableStateOf(false) }

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if(isMenuExtended) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        ),
        label = "Fab Animation"
    )

    val renderEffect: ComposeRenderEffect? = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getRenderEffect().asComposeRenderEffect()
    } else null

    Box {
        FabGroup(
            animationProgress = fabAnimationProgress,
            renderEffect = renderEffect
        )
        FabGroup(
            animationProgress = fabAnimationProgress,
        ) {
            isMenuExtended = isMenuExtended.not()
        }
    }
}

@Composable
private fun FabGroup(
    animationProgress: Float = 0f,
    renderEffect: ComposeRenderEffect? = null,
    toggleAnimation: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.renderEffect = renderEffect }
            .padding(bottom = 44.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedFab(
            icon = Icons.Default.Home,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 72.dp,
                        end = 210.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            opacity = LinearEasing.transform(.2f, .7f, animationProgress)
        )

        AnimatedFab(
            icon = Icons.Default.Settings,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 88.dp
                    ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
                ),
            opacity = LinearEasing.transform(.3f, .8f, animationProgress)
        )

        AnimatedFab(
            icon = Icons.Default.ShoppingCart,
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 72.dp,
                    start = 210.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress)
        )

        AnimatedFab(
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
        )

        AnimatedFab(
            icon = Icons.Default.Add,
            onClick = toggleAnimation,
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing.transform(0.35f, 0.65f, animationProgress)
                )
        )
    }
}

@Composable
private fun AnimatedFab(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = Color.Red,
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        modifier = modifier.scale(1.25f)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun getRenderEffect(): RenderEffect {
    val blurEffect = RenderEffect.createBlurEffect(80f, 80f, Shader.TileMode.MIRROR)

    val alphaFilterEffect = RenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(
            ColorMatrix(
                floatArrayOf(
                    1f, 0f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f, 0f,
                    0f, 0f, 1f, 0f, 0f,
                    0f, 0f, 0f, 50f, -5000f
                )
            )
        )
    )

    return RenderEffect.createChainEffect(alphaFilterEffect, blurEffect)
}

private fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

private operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)
