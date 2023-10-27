package com.techullurgy.composeuisapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.math.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

private val primaryColor = Color(0xfffa4f75)
private val backgroundColor = Color(0xff1f0d2e)

@Composable
fun ExpandableMenu(
    modifier: Modifier = Modifier,
    totalItems: Int = 9,
    selectedMenuIndex: Int = 0,
    menuItems: List<@Composable () -> Unit> = emptyList()
) {

    require(menuItems.size == totalItems) {
        "menuItems size must be equal to totalItems"
    }

    val animationProgresses = remember {
        Array(totalItems) {
            Animatable(0.01f).apply {
                updateBounds(lowerBound = 0.01f)
            }
        }.toList()
    }

    val coroutineScope = rememberCoroutineScope()

    val showHideMenu: () -> Unit =  {
        coroutineScope.launch {
            (0 until totalItems).forEach {
                launch {
                    animationProgresses[it].animateTo(
                        if (animationProgresses[it].value == 1f) 0f else 1f,
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
                delay(10)
            }
        }
    }

    val roundedPercentages = animationProgresses.map {
        map(it.value, 0.01f, 1f, 50f, 25f).toInt()
    }
    val opacities = animationProgresses.map {
        map(it.value, 0.01f, 1f, 0f, 1f)
    }

    val padding = map(animationProgresses[totalItems / 2].value, 0.01f, 1f, 8f, 16f)
    val gap = map(animationProgresses[totalItems / 2].value, 0.01f, 1f, 4f, 8f)
    val closeOpacity = map(animationProgresses[totalItems / 2].value, 0.01f, 1f, 0f, 1f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AnimatedVisibility(closeOpacity > 0.5f) {
            Column {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .clickable(onClick = showHideMenu)
                        .padding(4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        ExpandableHorizontalMenuLayout(
            gap = gap.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .background(primaryColor)
                .padding(padding.dp)
                .clickable(onClick = showHideMenu),
            contents = (0 until totalItems).map {
                @Composable {
                    if(opacities[it] > 0.5f) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(roundedPercentages[it]))
                                .alpha(opacities[it])
                                .background(color = if (it == selectedMenuIndex) Color.Transparent else backgroundColor)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            menuItems[it].invoke()
                        }
                    } else {
                        Box(modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(roundedPercentages[it]))
                            .background(color = backgroundColor)
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun ExpandableHorizontalMenuLayout(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    gap: Dp = 4.dp,
    contents: List<@Composable () -> Unit> = emptyList()
) {
    Layout(
        contents = contents,
        modifier = modifier,
        measurePolicy = ExpandableHorizontalGridMeasurePolicy(columns, gap)
    )
}

private class ExpandableHorizontalGridMeasurePolicy(
    private val columnCount: Int,
    private val gap: Dp
): MultiContentMeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<List<Measurable>>,
        constraints: Constraints
    ): MeasureResult {
        val placeablesList = measurables.map {
            it.map { measurable ->
                measurable.measure(constraints)
            }
        }
        var maxWidth = 0
        for(i in placeablesList.indices step columnCount) {
            var sum = 0
            for(j in 0 until columnCount) {
                if((i+j) < placeablesList.size) {
                    sum += placeablesList[i + j].maxOf { it.width }
                }
            }
            maxWidth = max(maxWidth, sum)
        }

        var maxHeight = 0
        for(i in 0 until columnCount) {
            var sum = 0
            for(j in placeablesList.indices step columnCount) {
                if((i+j) < placeablesList.size) {
                    sum += placeablesList[i + j].maxOf { it.height }
                }
            }
            maxHeight = max(maxHeight, sum)
        }

        val totalRows = (placeablesList.size / columnCount)
        val width = maxWidth + ((columnCount - 1) * gap.roundToPx())
        val height = maxHeight + ((totalRows - 1) * gap.roundToPx())

        return layout(width, height) {
            var currentX: Int
            var currentY = 0

            for (i in placeablesList.indices step columnCount) {
                currentX = 0
                var currentMaxHeight = 0
                for(j in 0 until columnCount) {
                    if((i+j) < placeablesList.size) {
                        val placeable = placeablesList[i + j].first()
                        placeable.place(currentX, currentY)
                        currentMaxHeight = max(currentMaxHeight, placeable.height)
                        currentX += placeable.width + gap.roundToPx()
                    }
                }
                currentY += currentMaxHeight + gap.roundToPx()
            }
        }
    }
}


data class GridMenuItem(
    val icon: ImageVector,
    val menuName: String,
    val contentDescription: String? = null
)


@Preview
@Composable
private fun ExpandableMenuPreview() {
    val menuItems = listOf(
        GridMenuItem(icon = Icons.Default.Home, menuName = "Home"),
        GridMenuItem(icon = Icons.Default.Search, menuName = "Search"),
        GridMenuItem(icon = Icons.Default.Person, menuName = "Person"),
        GridMenuItem(icon = Icons.Default.Favorite, menuName = "Favorite"),
        GridMenuItem(icon = Icons.Default.Email, menuName = "Email"),
        GridMenuItem(icon = Icons.Default.ShoppingCart, menuName = "ShoppingCart"),
        GridMenuItem(icon = Icons.Default.Share, menuName = "Share"),
        GridMenuItem(icon = Icons.Default.Settings, menuName = "Settings"),
        GridMenuItem(icon = Icons.Default.Menu, menuName = "Menu"),
    )

    var selectedMenuIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .padding(bottom = 32.dp), contentAlignment = Alignment.BottomCenter) {
        ExpandableMenu(
            totalItems = menuItems.size,
            selectedMenuIndex = selectedMenuIndex,
            menuItems = menuItems.mapIndexed { index, it ->
                @Composable {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = { selectedMenuIndex = index }) {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.contentDescription,
                                tint = if(index == selectedMenuIndex) Color.Black else Color.White,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}