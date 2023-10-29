package com.techullurgy.composeuisapplication.graphicslayertest

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.particlesystems.utils.nextFloat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

private val waterColor = Color(0xff00bfff)
private val waterTextColor = Color(0xff5044c4)


@Preview
@Composable
private fun WateryText() {
    Box(modifier = Modifier
        .size(300.dp)
        .background(Color.Black)) {

        val waveAnimationProgress = remember { Animatable(0f) }
        val flowAnimationProgress = remember { Animatable(0.497f) }

        val amount by remember(flowAnimationProgress.value) {
            derivedStateOf {
                (1200 * flowAnimationProgress.value).roundToInt()
            }
        }

        val textMeasurer = rememberTextMeasurer()

        val textLayoutResult = textMeasurer.measure(
            text = "$amount ml",
            style = LocalTextStyle.current.copy(
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold
            )
        )

        var wavesList by remember {
            mutableStateOf(emptyList<Wave>())
        }

        LaunchedEffect(key1 = Unit) {
            while(true) {
                val job1 = launch {
                    waveAnimationProgress.animateTo(
                        targetValue = 1f
                    )
                }
                job1.join()
                waveAnimationProgress.snapTo(0f)
            }
        }

        LaunchedEffect(key1 = Unit) {
            while(true) {
                val job2 = launch {
                    flowAnimationProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = 10000,
                            easing = CubicBezierEasing(.43f, .66f, .95f, .29f)
                        )
                    )
                }
                job2.join()
                delay(5000)
                flowAnimationProgress.snapTo(0f)
            }
        }

        LaunchedEffect(key1 = Unit) {
            snapshotFlow { waveAnimationProgress.value }.collectLatest {
                wavesList.forEach { wave ->
                    wave.addPhase(.5f * waveAnimationProgress.value)
                }
            }
        }

        Canvas(
            modifier = Modifier
                .alpha(.99f)
                .fillMaxSize()
        ) {
            val path = Path().apply {
                if(wavesList.isEmpty() || flowAnimationProgress.value == 0f) {
                    wavesList = List(5) {
                        Wave(
                            amplitude = Random.nextFloat(
                                from = (size.height * .001f),
                                until = (size.height * .0085f)
                            ),
                            period = Random.nextFloat(
                                from = (size.width / 18f),
                                until = (size.width / 4f)
                            ),
                            phase = Random.nextFloat(from = 0f, until = (2 * PI).toFloat())
                        )
                    }
                }

                val points = mutableListOf<Pair<Float, Float>>()
                for(x in 0..size.width.roundToInt() step 10) {
                    val y = wavesList.sumOf {
                        it.evaluate(x.toFloat()).toDouble()
                    }
                    points.add(
                        Pair(
                            first = x.toFloat(),
                            second = y.toFloat()
                        )
                    )
                }

                points.forEachIndexed { index, point ->
                    if(index == 0) {
                        moveTo(point.first, point.second + size.height*(1f - flowAnimationProgress.value))
                    } else {
                        lineTo(point.first, point.second + size.height*(1f - flowAnimationProgress.value))
                    }
                }

                lineTo(size.width, size.height*(1f - flowAnimationProgress.value))
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                lineTo(0f, size.height * .5f)
                close()
            }

            drawPath(
                path = path,
                color = waterColor
            )

            drawIntoCanvas {
                it.withSaveLayer(Rect(Offset.Zero, size), Paint()) {
                    drawText(
                        textLayoutResult = textLayoutResult,
                        color = Color.White,
                        topLeft = calculateTopLeft(center, textLayoutResult.size)
                    )

                    drawPath(
                        path = path,
                        color = waterTextColor,
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
        }
    }
}

private fun calculateTopLeft(
    center: Offset,
    size: IntSize
): Offset {
    return Rect(
        left = center.x - size.width/2,
        right = center.x + size.width/2,
        top = center.y - size.height/2,
        bottom = center.y + size.height/2
    ).topLeft
}

private data class Wave(
    // It determines the amplitude of the sine wave i.e 100 -> (100, -100)
    val amplitude: Float,
    // It determines no. of cycles of sine wave
    val period: Float,
    // It determines offset to start the sine wave
    val phase: Float
) {
    private var newPhase = phase
    fun evaluate(x: Float): Float {
        return (sin(newPhase + 2*PI * x/period) * amplitude).toFloat()
    }

    fun addPhase(value: Float) {
        newPhase += value
    }
}