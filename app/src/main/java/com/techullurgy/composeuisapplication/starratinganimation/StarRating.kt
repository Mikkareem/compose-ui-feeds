package com.techullurgy.composeuisapplication.starratinganimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun StarRatingAnimation() {

    val animation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animation.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 3000,
                    delayMillis = 1000,
                    easing = LinearOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(5f)) {
        val totalStars = 5
        val starWidth = size.width / totalStars
        val starHeight = size.height

        val path = Path().apply {
            moveTo(starWidth / 2f, 0f)
            lineTo(starWidth * 0.1f, starHeight)
            lineTo(starWidth, starHeight * .35f)
            lineTo(0f, starHeight * .35f)
            lineTo(starWidth * 0.9f, starHeight)
            close()
        }

        drawIntoCanvas {
            it.withSave {
                repeat(totalStars) {
                    translate(left = it * starWidth) {
                        drawPath(
                            path = path,
                            color = Color.Gray,
                        )
                    }
                }

                drawRect(
                    color = Color(0xffffd700),
                    size = Size(width = size.width * animation.value, height = size.height),
                    blendMode = BlendMode.SrcAtop
                )
            }
        }
    }
}