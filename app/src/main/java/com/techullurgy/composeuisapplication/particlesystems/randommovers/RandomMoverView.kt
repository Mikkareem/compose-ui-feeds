package com.techullurgy.composeuisapplication.particlesystems.randommovers

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.random.Random

@Preview
@Composable
fun RandomMoverView() {
    val particles: Array<RandomMover> = remember { Array(500) { RandomMover() } }

    LaunchedEffect(
        key1 = Unit,
        block = {
            for (particle in particles) {
                particle.applyForce(
                    Offset(
                        Random.nextDouble(-15.0, 15.0).toFloat(), Random.nextDouble(-15.0, 25.0).toFloat())
                )
            }
        }
    )

    LaunchedEffect(
        key1 = Unit,
        block = {
            while (true) {
                delay(10L)
                for (particle in particles) {
                    particle.update()
                }
            }
        }
    )

    Canvas(
        modifier = Modifier
            .onSizeChanged {
                for (particle in particles) {
                    particle.setInitialPosition(
                        Offset(
                            x = Random.nextInt(it.width).toFloat(),
                            y = Random.nextInt(it.height).toFloat()
                        )
                    )
                }
            }
            .fillMaxSize()
            .background(Color.Black),
        onDraw = {
            for (particle in particles) {
                particle.show(this)
            }
        }
    )
}