package com.techullurgy.composeuisapplication


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun DeckOfCard(
    animationFraction: Float,
    cards: List<CardModel> = cardModels
) {

    val rotationValue = interpolate(0f, 7f, animationFraction)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Green.copy(alpha = 0.75f)),
        contentAlignment = Alignment.Center
    ) {
        cards.forEach {
            NumberCard(number = it.identifier, color = it.color, rotation = it.angleFactor * rotationValue)
        }
    }
}

@Composable
private fun NumberCard(
    number: String,
    color: Color,
    modifier: Modifier = Modifier,
    rotation: Float = 0f
) {

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        number,
        style = LocalTextStyle.current.copy(
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .graphicsLayer {
                    rotationZ = rotation
                    transformOrigin = TransformOrigin(
                        pivotFractionX = 0f,
                        pivotFractionY = 1f
                    )
                }
                .border(2.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(color)
                .padding(16.dp)
        ) {

            val textCanvasDpSize = with(LocalDensity.current) {
                DpSize(textLayoutResult.size.width.toDp(), textLayoutResult.size.height.toDp())
            }

            Canvas(
                modifier = Modifier
                    .size(textCanvasDpSize)
                    .align(Alignment.TopStart)
            ) {
                drawText(textLayoutResult, color = Color.White)
            }

            Text(text = number, fontSize = 85.sp, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))

            Canvas(
                modifier = Modifier
                    .size(textCanvasDpSize)
                    .align(Alignment.BottomEnd)
            ) {
                rotate(180f) {
                    drawText(textLayoutResult, color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeckOfCardsRevealingAnimation() {
    val animatedValue = remember { Animatable(0f) }

    LaunchedEffect(key1 = Unit) {
        animatedValue.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    DeckOfCard(
        animationFraction = animatedValue.value
    )
}


private fun interpolate(left: Float, right: Float, fraction: Float): Float {
    return if(fraction == 0f) left else left + (right - left) * fraction
}

private data class CardModel(
    val identifier: String,
    val color: Color,
    val angleFactor: Int
)

private val cardModels = listOf(
    CardModel(
        identifier = "1",
        color = Color.Blue,
        angleFactor = -2
    ),
    CardModel(
        identifier = "2",
        color = Color.DarkGray,
        angleFactor = -1
    ),
    CardModel(
        identifier = "3",
        color = Color.Magenta,
        angleFactor = 0
    ),
    CardModel(
        identifier = "4",
        color = Color.Black,
        angleFactor = 1
    ),
    CardModel(
        identifier = "5",
        color = Color.Red,
        angleFactor = 2
    )
)