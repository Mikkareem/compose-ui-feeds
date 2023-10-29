package com.techullurgy.composeuisapplication.graphicslayertest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.R

@Preview
@Composable
private fun GlassyImage() {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_profile1),
            contentDescription = null,
            // Play Around with the scale to adjust the aspect ratio
            contentScale = FixedScale(1.3f)
        )
        Image(
            painter = painterResource(id = R.drawable.sample_profile1),
            contentDescription = null,
            contentScale = FixedScale(1f),
            modifier = Modifier.padding(32.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(24.dp)
                )
                .graphicsLayer {
                    renderEffect = BlurEffect(
                        radiusX = 1.dp.toPx(),
                        radiusY = 1.dp.toPx()
                    )
                    shape = RoundedCornerShape(24.dp)
                    clip = true
                }
        )
    }
}