package com.techullurgy.composeuisapplication.instagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InstagramProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(38.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Daniel Dup", fontWeight = FontWeight.Bold, fontSize = 23.sp)
            Spacer(modifier = Modifier.width(4.dp))
            VerifiedMark(modifier = Modifier.size(24.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(color = Color.Black)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "120", fontSize = 18.sp, fontWeight = FontWeight(1000))
                    Text(text = "Posts", fontSize = 16.sp)
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "120", fontSize = 18.sp, fontWeight = FontWeight(1000))
                    Text(text = "Followers", fontSize = 16.sp)
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "120", fontSize = 18.sp, fontWeight = FontWeight(1000))
                    Text(text = "Following", fontSize = 16.sp)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(35))
                    .background(Color.Blue)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Follow", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(24.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(35))
                    .background(Color.DarkGray)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Message", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Suggested", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                SuggestedFollowerCard()
            }
        }
    }
}

@Composable
fun VerifiedMark(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val padding = size.width * .1f
            val radius = size.width / 2 - padding
            val totalCombs = 10


            val rect = Rect(Offset.Zero, radius)
            val tempPath = Path().apply {
                arcTo(rect = rect, startAngleDegrees = 0f, sweepAngleDegrees = 359.99f, true)
            }

            val path = Path().apply {
                repeat(totalCombs) {
                    val startAngleInRadians = (PI / 180f) * (it * (360f/ totalCombs))
                    val x = (radius * cos(startAngleInRadians)).toFloat()
                    val y = (radius * sin(startAngleInRadians)).toFloat()

                    val endAngleInRadians = (PI / 180f) * ((it+1) * (360f/ totalCombs))
                    val nextX = (radius * cos(endAngleInRadians)).toFloat()
                    val nextY = (radius * sin(endAngleInRadians)).toFloat()

                    val middleAngleInRadians = (startAngleInRadians + endAngleInRadians) / 2f
                    val middleX = ((radius + (padding * 2f)) * cos(middleAngleInRadians)).toFloat()
                    val middleY = ((radius + (padding * 2f)) * sin(middleAngleInRadians)).toFloat()

                    moveTo(x, y)
                    quadraticBezierTo(middleX, middleY, nextX, nextY)
                }
                addPath(tempPath)
            }

            translate(left = center.x, top = center.y) {
                drawPath(path = path, color = Color.Blue)

                translate(left = padding * 0.5f, top = padding * 0.5f) {
                    val startX = (padding * 2f) * cos((PI/180f) * (180f + 45f))
                    val startY = (padding * 2f) * sin((PI/180f) * (180f + 45f))
                    drawLine(
                        color = Color.White,
                        start = Offset(startX.toFloat() - (padding * 1f), startY.toFloat() + padding),
                        end = Offset(x = -(padding * 1f), y = padding),
                        strokeWidth = padding
                    )

                    val endX = (padding * 4f) * cos((PI/180f) * (270f + 45f))
                    val endY = (padding * 4f) * sin((PI/180f) * (270f + 45f))
                    drawLine(
                        color = Color.White,
                        start = Offset(endX.toFloat() - (padding * 1f), endY.toFloat() + padding),
                        end = Offset(x = -(padding * 1f), y = padding),
                        strokeWidth = padding
                    )
                }

            }


        }
    }
}

@Composable
fun SuggestedFollowerCard() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15))
            .background(color = Color.LightGray)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Aishwarya", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "12 mutual followers")
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(35))
                    .background(Color.Blue)
                    .padding(vertical = 8.dp, horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Follow", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}


@Preview()
@Composable
fun InstagramProfileScreenPreview() {
    InstagramProfileScreen()
}