package com.techullurgy.composeuisapplication.graphicslayertest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun BlendModeTest2() {
    Box(
        modifier = Modifier
            .size(600.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .graphicsLayer {
//                    compositingStrategy = CompositingStrategy.Offscreen
                }
        ) {
            val radius = size.width / 2

            drawCircle(
                color = Color.White,
                center = center,
                radius = radius
            )

            drawCircle(
                color = Color(0xffe91e63),
                center = center,
                radius = radius * .6f,
            )

            drawCircle(
                color = Color(0xff2196f3),
                center = Offset(
                    x = center.x + radius * .3f,
                    y = center.y + radius * .3f
                ),
                radius = radius * .4f,
                blendMode = BlendMode.Modulate
            )
        }
    }
}