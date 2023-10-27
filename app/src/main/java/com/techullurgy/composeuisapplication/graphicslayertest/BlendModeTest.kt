package com.techullurgy.composeuisapplication.graphicslayertest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CompositingStrategyOffScreenExample() {
    Box(
        modifier = Modifier
            .size(400.dp)
            .background(color = Color.Magenta)
            .padding(24.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    oval = Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )

                onDrawWithContent {
                    clipPath(path) {
                        this@onDrawWithContent.drawContent()
                    }

                    val dotSize = size.width / 8f
                    val dotCenter = Offset(
                        x = size.width - dotSize,
                        y = size.height - dotSize
                    )
                    drawCircle(
                        color = Color.Black,
                        radius = dotSize,
                        center = dotCenter,
                        blendMode = BlendMode.Clear
                    )

                    drawCircle(
                        color = Color.Green,
                        radius = dotSize * .8f,
                        center = dotCenter,
                    )
                }
            }
    ) {
        Box(modifier = Modifier.size(700.dp).background(color = Color.Blue))
    }
}