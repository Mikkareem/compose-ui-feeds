package com.techullurgy.composeuisapplication.graph.staticgraph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun StaticGraphView(
    dataPoints: List<Float> = listOf(0.6f, 0.43f, 0.29f, 0.92f, 0.72f, 0.14f, 0.81f, 0.44f, 0.65f)
) {
    val staticGraph = rememberStaticGraphState(dataPoints = dataPoints)

    var currentX by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = Modifier
            .size(300.dp)
            .draggable(
                state = DraggableState {
                    currentX += it
                    staticGraph.followAtX(currentX)
                },
                onDragStarted = {
                    currentX = it.x
                },
                orientation = Orientation.Horizontal
            )
    ) {
        staticGraph.show(this)
    }
}