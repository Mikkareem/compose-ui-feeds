package com.techullurgy.composeuisapplication.analogclock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ClockView(
    hour: Int = 2,
    minute: Int = 0,
    second: Int = 0
) {

    Canvas(modifier = Modifier.background(color = Color.Magenta).padding(10.dp).size(300.dp)) {

        val secondHandDegree: Float = -90f + second * 6f
        val minuteHandDegree: Float = -90f + minute * 6f + (second * .1f)
        val hourHandDegree: Float = -90f + (hour * 5) * 6f + (minute * .5f)

        drawSecondHandle(secondHandDegree)
        drawMinuteHandle(minuteHandDegree)
        drawHourHandle(hourHandDegree)
        drawClockOutlines()
    }
}

private fun DrawScope.drawClockOutlines() {
    drawCircle(
        color = Color.Black,
        radius = size.width / 2,
        style = Stroke(width = 10.dp.toPx())
    )

    drawCircle(
        color = Color.Yellow,
        radius = 3.dp.toPx(),
    )

    repeat(60) {
        rotate(it * 6f) {
            if(it % 5 == 0) {
                if(it % 15 == 0) {
                    drawLine(
                        color = Color.Green,
                        start = Offset(size.width - 45.dp.toPx(), size.height / 2),
                        end = Offset(size.width - 10.dp.toPx(), size.height / 2),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                } else {
                    drawLine(
                        color = Color.Black,
                        start = Offset(size.width - 30.dp.toPx(), size.height / 2),
                        end = Offset(size.width - 10.dp.toPx(), size.height / 2),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            } else {
                drawLine(
                    color = Color.Black,
                    start = Offset(size.width - 20.dp.toPx(), size.height / 2),
                    end = Offset(size.width - 10.dp.toPx(), size.height / 2),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

private fun DrawScope.drawHourHandle(degree: Float) {
    rotate(degree) {
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 2, size.height / 2),
            end = Offset(size.width - 100.dp.toPx(), size.height / 2),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawMinuteHandle(degree: Float) {
    rotate(degree) {
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 2, size.height / 2),
            end = Offset(size.width - 70.dp.toPx(), size.height / 2),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawSecondHandle(degree: Float) {
    rotate(degree) {
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 2, size.height / 2),
            end = Offset(size.width - 50.dp.toPx(), size.height / 2),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}