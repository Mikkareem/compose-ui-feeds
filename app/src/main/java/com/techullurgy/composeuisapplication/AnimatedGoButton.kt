package com.techullurgy.composeuisapplication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
private fun AnimatedGoButton(
    modifier: Modifier = Modifier.size(24.dp),
    strokeWidth: Dp = 3.dp,
    color: Color = Color.Red,
    onClick: () -> Unit = {}
) {

    val canRotate = false

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200)
        )
    }

    Canvas(
        modifier = modifier
            .padding(strokeWidth)
            .clickable(onClick = onClick)
    ) {

        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 359.99f * animationProgress.value,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx())
        )

        val path = Path().apply {
            moveTo(size.width * .25f, center.y)
            lineTo(size.width * .75f, center.y)

            lineTo(size.width * .55f, size.height * .35f)
            moveTo(size.width * .75f, center.y)
            lineTo(size.width * .55f, size.height * .65f)
        }

        scale(
            scale = animationProgress.value,
            pivot = Offset(size.width * .25f, center.y)
        ) {
            rotate(if(canRotate) 360f * animationProgress.value else 0f) {
                drawPath(
                    path = path,
                    color = color.copy(alpha = animationProgress.value),
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimatedGoButtonPreview() {

    var count by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = Unit) {
        while (count < 100) {
            count++
            if(count % 2 == 0) {
                delay(1000)
            } else {
                delay(3000)
            }
        }
    }

    Box(modifier = Modifier
        .size(400.dp)
        .background(Color.White)) {
        if(count % 2 != 0) {
            AnimatedGoButton(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}