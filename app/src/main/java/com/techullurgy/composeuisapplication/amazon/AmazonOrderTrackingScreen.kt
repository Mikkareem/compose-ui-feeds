package com.techullurgy.composeuisapplication.amazon

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
private fun AmazonOrderTrackingView(
    orderTracking: AmazonOrderTracking = AmazonOrderTracking()
) {
    val textMeasurer = rememberTextMeasurer()
    val textStyle = LocalTextStyle.current

    val animations = remember {
        orderTracking.percentages.map { Animatable(0f) }
    }

    LaunchedEffect(orderTracking.percentages) {
        delay(10000)
        animations.forEachIndexed { index, it ->
            val animJob = launch {
                it.animateTo(
                    targetValue = orderTracking.percentages[index],
                    animationSpec = tween(durationMillis = 2500)
                )
            }
            animJob.join()
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .background(Color(0xffcdd3ff).copy(alpha = 0.5f))
            .padding(16.dp)
    ) {
        val tops = size.height / 4

        repeat(4) {
            if(it < 3) {
                drawRect(
                    color = Color.LightGray,
                    topLeft = Offset(x = 12.dp.toPx(), y = it * tops),
                    size = Size(width = 6.dp.toPx(), height = tops)
                )
                if(it < orderTracking.percentages.size) {
                    drawRect(
                        color = Color(0xff50bfb6),
                        topLeft = Offset(x = 12.dp.toPx(), y = it * tops),
                        size = Size(
                            width = 6.dp.toPx(),
                            height = tops * animations[it].value
                        )
                    )
                }
            }

            drawRect(
                color = if(it < animations.size && animations[it].value > 0) {
                    Color(0xff50bfb6)
                } else {
                    Color.LightGray
                },
                size = Size(30.dp.toPx(), 30.dp.toPx()),
                topLeft = Offset(x = 0f, y = it * tops)
            )

        }

        val tickPath = Path().apply {
            val tickSize = 30.dp.toPx()
            moveTo(x = tickSize * .2f, y = tickSize * .4f)
            lineTo(x = tickSize * .40f, y = tickSize * .65f)
            lineTo(x = tickSize * .85f, y = tickSize * .25f)
        }

        repeat(orderTracking.percentages.size) {
            translate(top = it * tops) {
                drawPath(path = tickPath, color = Color.White,style = Stroke(width = 5.dp.toPx()))
            }
        }

        val texts = listOf(
            textMeasurer.measure(
                text = "Ordered ${orderTracking.orderDate}",
                style = textStyle.copy(fontSize = 20.sp)
            ),
            textMeasurer.measure(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Shipped ${orderTracking.shippedDate}\n")
                    }
                    withStyle(SpanStyle(color = Color(0xff4dc0ba).copy(alpha = 0.7f), fontStyle = FontStyle.Italic, fontSize = 15.sp, fontWeight = FontWeight(500))) {
                        append("Package arrived at the final delivery station at 2:55 PM, ")
                        append("${orderTracking.currentShippedState}\n")
                    }
                    withStyle(SpanStyle(color = Color(0xff50bfb6), fontSize = 18.sp)) {
                        append("See all updates")
                    }
                },
                style = textStyle.copy(fontSize = 20.sp),
                constraints = Constraints.fixedWidth((size.width - 30.dp.toPx() - 50.dp.toPx()).toInt())
            ),
            textMeasurer.measure(
                text = "Out For Delivery",
                style = textStyle.copy(fontSize = 20.sp)
            ),
            textMeasurer.measure(
                text = "Arriving ${orderTracking.expectedArrivalDate}",
                style = textStyle.copy(fontSize = 20.sp)
            )
        )

        texts.forEachIndexed { index, it ->
            drawText(
                textLayoutResult = it,
                topLeft = Offset(x = 50.dp.toPx(), y = index * tops)
            )
        }
    }
}

private data class AmazonOrderTracking(
    val orderDate: String = "27 Oct 2023",
    val shippedDate: String? = "28 Oct 2023",
    val currentShippedState: String? = "30 Oct 2023",
    val isOutForDelivery: Boolean = false,
    val expectedArrivalDate: String = "31 Oct 2023"
) {
    val percentages = mutableListOf<Float>().apply {
        // We can add the percentages based on some calculation.
        add(1f)
        add(.7f)
    }.toList()
}