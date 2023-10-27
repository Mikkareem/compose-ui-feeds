package com.techullurgy.composeuisapplication.particlesystems.indication

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.particlesystems.utils.nextFloat
import com.techullurgy.composeuisapplication.particlesystems.utils.random
import com.techullurgy.composeuisapplication.particlesystems.utils.randomMirror
import com.techullurgy.composeuisapplication.particlesystems.utils.x
import com.techullurgy.composeuisapplication.particlesystems.utils.xTimes
import com.techullurgy.composeuisapplication.particlesystems.utils.y
import com.techullurgy.composeuisapplication.particlesystems.utils.yTimes
import kotlinx.coroutines.flow.collectLatest
import kotlin.random.Random

class IndicationParticle {
    private var velocity: Offset = Offset.randomMirror(10f, 10f)

    private var position: Offset by mutableStateOf(Offset.Zero)

    private val color: Color
            = Color(
        red = Random.nextInt(255),
        green = Random.nextInt(255),
        blue = Random.nextInt(255)
    )

    private val radius: Float = Random.nextFloat(10.0f, 20.0f)

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

    private fun update() {
        position = position.plus(velocity)
    }

    fun show(scope: DrawScope) {
        update()
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


object MyIndication: Indication {
    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val instance = remember(interactionSource) { MyIndicationInstance() }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collectLatest { interaction ->
                when(interaction) {
                    is PressInteraction.Press -> instance.show(true)
                    is PressInteraction.Release -> instance.show(false)
                    is PressInteraction.Cancel -> instance.show(false)
                    is HoverInteraction.Enter -> instance.show(true)
                    is HoverInteraction.Exit -> instance.show(false)
                }
            }
        }

        return instance
    }

    private class MyIndicationInstance: IndicationInstance {
        private var shouldShowIndicationNow: Boolean by mutableStateOf(false)
        private val randomParticles: SnapshotStateList<IndicationParticle> = mutableStateListOf()

        fun show(value: Boolean) {
            shouldShowIndicationNow = value
        }

        private fun DrawScope.initiateParticles() {
            if(randomParticles.isNotEmpty()) {
                return
            }

            for(i in 1..50) {
                randomParticles.add(
                    IndicationParticle().apply {
                        setInitialPosition(
                            Offset.random(xMax = size.width, yMax = size.height)
                        )
                    }
                )
            }
        }

        private fun DrawScope.drawMovers() {
            initiateParticles()

            randomParticles.forEach {
                it.show(this)
            }
        }

        override fun ContentDrawScope.drawIndication() {
            drawContent()

            if(shouldShowIndicationNow) {
                drawRect(color = Color.Red.copy(alpha = 0.33f))

                drawMovers()
            }
        }
    }
}

@Preview
@Composable
fun Screen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val interactionSource = remember { MutableInteractionSource() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable(
                    onClick = { },
                    interactionSource = interactionSource,
                    indication = MyIndication
                )
                .padding(23.dp)
        ) {
            Text(text = "Press/LongPress Me!! Please", fontSize = 23.sp)
        }
    }
}