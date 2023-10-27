package com.techullurgy.composeuisapplication.particlesystems.randommovers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.techullurgy.composeuisapplication.particlesystems.utils.x
import com.techullurgy.composeuisapplication.particlesystems.utils.xTimes
import com.techullurgy.composeuisapplication.particlesystems.utils.y
import com.techullurgy.composeuisapplication.particlesystems.utils.yTimes
import kotlin.random.Random

class RandomMover {
    private var acceleration: Offset = Offset.Zero
    private var velocity: Offset = Offset.Zero

    private var position: Offset by mutableStateOf(Offset.Zero)
    private val color: Color
        = Color(
            red = Random.nextInt(255),
            green = Random.nextInt(255),
            blue = Random.nextInt(255)
        )

    private val radius: Float = Random.nextDouble(10.0, 20.0).toFloat()

    private fun DrawScope.edgeDetectionChanges() {
        if(position.x < 0f || position.x > size.width) {
            position = if(position.x < 0f) {
                position.x(0f)
            } else {
                position.x(size.width)
            }
            velocity = velocity.xTimes(-1f)
        } else if(position.y < 0f || position.y > size.height) {
            position = if(position.y < 0f) {
                position.y(0f)
            } else {
                position.y(size.height)
            }
            velocity = velocity.yTimes(-1f)
        }
    }

    fun setInitialPosition(startPosition: Offset) {
        position = startPosition
    }

    fun applyForce(force: Offset) {
        acceleration = acceleration.plus(force)
    }

    fun update() {
        velocity = velocity.plus(acceleration)
        position = position.plus(velocity)
        acceleration = acceleration.times(0f)
    }

    fun show(scope: DrawScope) {
        scope.showParticle()
    }

    private fun DrawScope.showParticle() {
        edgeDetectionChanges()

        drawCircle(
            color = color,
            radius = radius,
            center = position
        )
    }
}