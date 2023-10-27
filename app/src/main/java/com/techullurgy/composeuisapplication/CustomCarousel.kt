package com.techullurgy.composeuisapplication

import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.math.lerp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CustomCarousel() {
    val pagerState = rememberPagerState { 5 }

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = isDragged) {
        if(isDragged.not()) {
            while (true) {
                delay(5000)
                pagerState.animateScrollToPage(
                    page = if(pagerState.settledPage + 1 >= pagerState.pageCount) 0 else pagerState.settledPage + 1,
                    animationSpec = tween(durationMillis = 2000, easing = EaseInOutQuint)
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth().background(color = Color.Blue), contentAlignment = Alignment.Center) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            pageSize = PageSize.Fixed(200.dp),
            modifier = Modifier.width(250.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
        ) {
            CarouselItem(
                modifier = Modifier.graphicsLayer {
                    val pageOffset = ((pagerState.currentPage - it) + pagerState.currentPageOffsetFraction).absoluteValue

                    val transformation = lerp(start = 0.7f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    scaleY = transformation
                    alpha = transformation
                }
            )
        }
    }
}

@Composable
private fun CarouselItem(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Image(painter = painterResource(id = R.drawable.sample_profile1), contentDescription = null)
    }
}

