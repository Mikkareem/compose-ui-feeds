package com.techullurgy.composeuisapplication.graph.staticgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.graph.BezierConfiguration

@Composable
fun rememberStaticGraphState(dataPoints: List<Float>): StaticGraph = remember(dataPoints) {
    StaticGraph(dataPoints)
}


class StaticGraph(
    private val dataPoints: List<Float>
) {

    private var xStep: Float = 0f

    private lateinit var dataOffsets: List<Offset>
    private lateinit var lerpedPoints: List<Offset>

    private var followPoint: Offset by mutableStateOf(Offset.Zero)

    fun show(drawScope: DrawScope) {
        drawScope.drawGraph()
    }

    fun followAtX(x: Float) {
        if(x < xStep || x >= xStep+lerpedPoints.size) return

        val consideredX = (x - xStep).toInt()
        followPoint = lerpedPoints[consideredX]
    }

    private fun DrawScope.initiateGraph() {

        if(!::dataOffsets.isInitialized) {
            xStep = size.width / dataPoints.size
            dataOffsets = dataPoints.mapIndexed { index, value ->
                Offset(
                    x = (index+1) * xStep,
                    y = size.height * value
                )
            }
            followPoint = dataOffsets.first()
        }

        if(!::lerpedPoints.isInitialized) {
            lerpedPoints = BezierConfiguration.getLerpedDetailsOfPoints(dataOffsets, xStep.toInt())
        }
    }

    private fun DrawScope.drawGraph() {
        initiateGraph()

        val path = Path()
        path.moveTo(dataOffsets.first().x, dataOffsets.first().y)

        lerpedPoints.forEach {
            path.lineTo(it.x, it.y)
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 3.dp.toPx())
        )

        drawPoints(
            points = dataOffsets,
            color = Color.Green,
            pointMode = PointMode.Points,
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawCircle(
            color = Color.Magenta,
            radius = 10f,
            center = followPoint
        )
    }
}