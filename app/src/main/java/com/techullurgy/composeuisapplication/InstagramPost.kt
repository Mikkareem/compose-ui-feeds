package com.techullurgy.composeuisapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Preview
@Composable
fun InstagramPost() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(8.dp)
    ) {
        TopSection()
        PostSection()
        Spacer(modifier = Modifier.height(4.dp))
        BottomSection()
        Spacer(modifier = Modifier.height(4.dp))
        TextsSection()
    }
}

//@Preview
@Composable
private fun TopSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = Color.Blue)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Username", fontSize = 24.sp, letterSpacing = (-0.45).sp)
        }
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
    }
}

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostSection() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .background(color = Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "🥲", fontSize = 144.sp)
        Badge(containerColor = Color.Black, contentColor = Color.White, modifier = Modifier
            .padding(10.dp)
            .align(Alignment.TopEnd)) {
            Text(text = "2/10", fontSize = 16.sp, letterSpacing = (-0.45).sp, modifier = Modifier.padding(3.dp))
        }
    }
}

//@Preview
@Composable
private fun BottomSection() {
    Row(
        horizontalArrangement = InstagramPostControllerArrangement,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Icon(imageVector = Icons.Outlined.Email, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Icon(imageVector = Icons.Outlined.Share, contentDescription = null, modifier = Modifier.size(32.dp))
        }
        CountProgress(count = 10, index = 1)
        Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun CountProgress(count: Int, index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        repeat(count) {
            Box(modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color = if (it == index) Color.Magenta else Color.Gray.copy(alpha = 0.7f)))
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

private object InstagramPostControllerArrangement: Arrangement.Horizontal {
    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        layoutDirection: LayoutDirection,
        outPositions: IntArray
    ) {
        require(sizes.size == 3) { "Only 3 composable are allowed... " }
        if(layoutDirection == LayoutDirection.Ltr) {
            outPositions[0] = 0
            outPositions[1] = totalSize / 2 - sizes[1] / 2
            outPositions[2] = totalSize - sizes[2]
        } else {
            outPositions[2] = 0
            outPositions[1] = totalSize / 2 - sizes[1] / 2
            outPositions[0] = totalSize - sizes[0]
        }
    }
}

@Composable
private fun TextsSection() {
    Column {
        CompositionLocalProvider(LocalTextStyle provides TextStyle(fontSize = 18.sp)) {
            Text(text = "10,328 views")
            Row {
                Text(text = "Username", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Instagram Template ")
                        val spanStyle = SpanStyle(color = Color.Blue)
                        withStyle(spanStyle) {
                            append("#template")
                        }
                    }
                )
            }
            Text(text = "View all 328 comments", color = Color.Gray.copy(0.8f))
            Text(text = "5 Days ago".uppercase(Locale.ROOT), color = Color.Gray.copy(0.5f))
        }
    }
}