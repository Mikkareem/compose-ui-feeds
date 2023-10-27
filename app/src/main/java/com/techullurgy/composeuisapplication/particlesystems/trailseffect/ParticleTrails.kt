package com.techullurgy.composeuisapplication.particlesystems.trailseffect

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import com.techullurgy.composeuisapplication.math.map

class ParticleTrails {
    private val trails: SnapshotStateList<Offset> = mutableStateListOf()
    private var position: Offset by mutableStateOf(Offset.Unspecified)

    private var velocity: Offset = Offset(0f, -10f)
    private var acceleration: Offset = Offset.Zero

    private val gravity: Offset = Offset(0f, 0.0006f)

    private val color: Color = Color(0.73f, 0.46f, 0.83f, 1f, ColorSpaces.Bt2020)

    private fun applyGravity() {
        acceleration = acceleration.plus(gravity)
    }

    private fun update() {
        for(i in (0 until (trails.size-1)).reversed()) {
            trails[i + 1] = trails[i]
        }
        trails[0] = position
        applyGravity()
        velocity = velocity.plus(acceleration)
        position = position.plus(velocity)
    }

    fun show(scope: DrawScope) {
        if(position.isSpecified) update()
        scope.showTrailedParticles()
    }

    private fun DrawScope.showTrailedParticles() {
        if(position.isUnspecified) {
            position = Offset(size.width / 2, size.height - 400f)
            for (i in 0 until 100) {
                trails.add(Offset(position.x, position.y - i*velocity.y))
            }
        }

        drawCircle(
            color = color,
            radius = 20f,
            center = position
        )

        for(i in trails.indices.reversed()) {
            val radius = map(i.toFloat(), 0f, trails.size.toFloat()-1, 20f, 5f)
            val alpha = map(i.toFloat(), 0f, trails.size.toFloat()-1, 0.1f, 0.003f)
            drawCircle(
                color = color.copy(alpha = alpha),
                radius = radius,
                center = trails[i]
            )
        }
    }
}

@Preview
@Composable
fun ParticleTrailsView() {
    val particleTrails = remember {
        ParticleTrails()
    }

    var counter by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            particleTrails.show(this)
        }
        Button(onClick = { counter++ }) {
            Text(text = "Counter $counter")
        }
    }
}