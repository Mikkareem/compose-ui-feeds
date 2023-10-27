package com.techullurgy.composeuisapplication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.math.map
import kotlinx.coroutines.launch

@Preview
@Composable
private fun OscillatingHeartAnimation() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(200, 100, 0)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val translationValue = remember { Animatable(0f) }

        LaunchedEffect(key1 = Unit) {
            launch {
                translationValue.animateTo(
                    targetValue = -100f,
                    animationSpec = InfiniteRepeatableSpec(
                        animation = tween(800),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(bottom = 60.dp)
                .drawBehind {
                    val startX = map(translationValue.value, -100f, 0f, 0f, .5f)
                    val startOffset = Offset(center.x - (size.width * startX), size.height)
                    val endOffset = Offset(center.x + (size.width * startX), size.height)

                    drawLine(color = Color.White, strokeWidth = 3.dp.toPx(), start = startOffset, end = endOffset)
                },
            contentAlignment = Alignment.Center
        ) {
            val animationProgress = map(translationValue.value, -100f, 0f, 0f, 1f)
            val animatedColor = lerp(Color.White, Color.Black, animationProgress)

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = animatedColor,
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {

                        val scaleFactor = map(translationValue.value, -100f, 0f, 3f, 1f)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        translationY = translationValue.value * 8
                    }
            )
        }
    }
}