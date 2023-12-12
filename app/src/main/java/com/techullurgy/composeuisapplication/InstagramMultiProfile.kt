package com.techullurgy.composeuisapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
private fun MultiProfileView() {
    InstagramMultiProfileContainer(
        gap = 35.dp,
        profiles = listOf(
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.sample_profile2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            },
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.sample_profile1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            },
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.sample_profile2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            },
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.sample_profile1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            },
            @Composable {
                Image(
                    painter = painterResource(id = R.drawable.sample_profile2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            },
        )
    )
}