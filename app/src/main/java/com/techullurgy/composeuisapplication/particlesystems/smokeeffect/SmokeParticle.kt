package com.techullurgy.composeuisapplication.particlesystems.smokeeffect

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import com.techullurgy.composeuisapplication.particlesystems.utils.nextFloat
import kotlin.random.Random

class SmokeParticle(
    initialPosition: Offset
) {
    private var position: Offset by mutableStateOf(initialPosition)

    private var velocity: Offset
        = Offset(
            x = Random.nextFloat(-2f, 2f),
            y = Random.nextFloat(-7f, -2f)
        )

    private var alpha: Float = 1f

    private fun update() {
        alpha -= 0.00875f
        position = position.plus(velocity)
    }

    fun show(drawScope: DrawScope) {
        update()
        drawScope.showParticle()
    }

    fun canRemove(): Boolean = alpha < 0f

    private fun DrawScope.showParticle() {
        drawCircle(
            color = Color.White.copy(alpha = if(alpha >= 0f) alpha else 0f),
            radius = 20f,
            center = position
        )
    }
}

@Preview
@Composable
fun SmokeParticleView() {

    var smokeParticles = remember {
        mutableStateListOf<SmokeParticle>()
    }

    Canvas(
        modifier = Modifier
            .onSizeChanged {
                for (i in 0 until 1) {
                    smokeParticles.add(
                        SmokeParticle(
                            Offset(
                                x = it.width / 2f,
                                y = it.height * 0.6f
                            )
                        )
                    )
                }
            }
            .fillMaxSize()
            .background(color = Color.Black),
    ) {

        smokeParticles = smokeParticles.filter { !it.canRemove() }.toMutableStateList()


        for(i in 1..5) {
            smokeParticles.add(
                SmokeParticle(
                    Offset(
                        x = size.width / 2f,
                        y = size.height * 0.6f
                    )
                )
            )
        }

        for(particle in smokeParticles) {
            particle.show(this)
        }
    }
}