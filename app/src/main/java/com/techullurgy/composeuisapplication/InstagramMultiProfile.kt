package com.techullurgy.composeuisapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InstagramMultiProfileContainer(
    profiles: List<@Composable ()->Unit>,
    modifier: Modifier = Modifier,
    gap: Dp = 20.dp
) {
    Layout(contents = profiles, modifier = modifier) { listOfMeasurables, constraints ->
        val listOfPlaceables = listOfMeasurables.map { measurables -> measurables.map { measurable -> measurable.measure(constraints) } }

        val placeableHeight = listOfPlaceables.maxOf { placeables -> placeables.maxOf { it.height } }

        val containerWidth = listOfPlaceables.last().maxOf { it.width }

        val placeableWidth = ((listOfPlaceables.size - 1) * gap.roundToPx()) + containerWidth

        layout(placeableWidth, placeableHeight) {
            var currentOffsetX = 0

            listOfPlaceables.map { placeables ->
                placeables.map { placeable ->
                    placeable.place(currentOffsetX, 0)
                }
                currentOffsetX += gap.roundToPx()
            }
        }
    }
}