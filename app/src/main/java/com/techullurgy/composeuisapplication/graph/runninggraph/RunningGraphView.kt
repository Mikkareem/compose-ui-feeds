package com.techullurgy.composeuisapplication.graph.runninggraph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Preview
@Composable
fun BezierGraph(
    timeStep: Int = 20
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val runningGraph: RunningGraph? = rememberRunningGraphState(size = size)

    LaunchedEffect(runningGraph) {
        while (true) {
            delay(100)
            runningGraph?.tick()
        }
    }

    Canvas(
        modifier = Modifier
            .onSizeChanged {
                size = it
            }
            .size(300.dp)
            .background(color = Color.Cyan)
    ) {

        runningGraph?.let {
            drawGraph(lerpedPoints = it.lerpedPoints)

            drawReferenceLine(
                start = Offset(0f, it.travelAnchorPoint.y),
                end = Offset(this.size.width, it.travelAnchorPoint.y)
            )

            drawTravelAnchorPoint(at = it.travelAnchorPoint)

            drawApproachPoint(at = it.approachPoint)
        }
    }

}

private fun DrawScope.drawGraph(
    lerpedPoints: List<Offset>,
    color: Color = Color.Blue,
    strokeWidth: Dp = 5.dp
) {
    val path = Path()
    for(index in lerpedPoints.indices) {
        val value = lerpedPoints[index]
        if (index == 0) {
            path.moveTo(value.x, value.y)
        }
        path.lineTo(value.x, value.y)
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth.toPx())
    )
}

private fun DrawScope.drawTravelAnchorPoint(
    at: Offset,
    innerColor: Color = Color.Magenta,
    outlineColor: Color = innerColor,
    outlineRadius: Dp = 8.dp,
    outlineStrokeWidth: Dp = 3.dp,
    innerStrokeWidth: Dp = 10.dp
) {
    drawCircle(
        color = outlineColor,
        radius = outlineRadius.toPx(),
        style = Stroke(width = outlineStrokeWidth.toPx()),
        center = at
    )

    drawPoints(
        points = listOf(at),
        pointMode = PointMode.Points,
        color = innerColor,
        strokeWidth = innerStrokeWidth.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawApproachPoint(
    at: Offset,
    color: Color = Color.Black,
    strokeWidth: Float = 10.dp.toPx(),
    cap: StrokeCap = StrokeCap.Round
) {
    drawCircle(
        color = color,
        radius = 8.dp.toPx(),
        style = Stroke(width = 3.dp.toPx()),
        center = at
    )
    drawPoints(
        points = listOf(at),
        pointMode = PointMode.Points,
        color = color,
        strokeWidth = strokeWidth,
        cap = cap
    )
}

private fun DrawScope.drawReferenceLine(
    start: Offset,
    end: Offset,
    color: Color = Color.Black,
    strokeWidth: Float = 3.dp.toPx()
) {
    drawLine(
        color = color,
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(5f, 8f),
            phase = 5f
        ),
        start = start,
        end = end,
        strokeWidth = strokeWidth
    )
}