package com.techullurgy.composeuisapplication.foodappdesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.R

@Preview
@Composable
fun FoodOrderDetailsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff6f7f7))
                .padding(16.dp)
        ) {
            TopBar()
            Spacer(modifier = Modifier.height(80.dp))
            FoodSpecialDealsMainSection()
        }
        BottomSection(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun TopBar() {
    Row {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Text(text = "Special Deals")
        }
        Icon(imageVector = Icons.TwoTone.Menu, contentDescription = null)
    }
}

@Composable
private fun FoodSpecialDealsMainSection() {
    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sake sushi", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$4.99", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "4.9 (109 Reviews)", fontSize = 16.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Image(painter = painterResource(id = R.drawable.food2), contentDescription = null)
    }
}


@Composable
private fun BottomSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color(0xff3f434b))
            .padding(16.dp)
    ) {
        ProvideTextStyle(value = LocalTextStyle.current.copy(color = Color.White)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sample_profile2),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "Trojan Fox", fontSize = 24.sp)
                    Text(text = "Your Courier")
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xfff46859))
                        .padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xfff46859))
                        .padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                ProvideTextStyle(LocalTextStyle.current.copy(color = Color.Black)) {
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xfff46859).copy(alpha = 0.2f))
                                .padding(8.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.baseline_access_time_24), contentDescription = null, tint = Color(0xfff46859))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Your Delivery time", color = LocalTextStyle.current.color.copy(alpha = 0.5f))
                            Text(text = "12 Minutes", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xfff46859).copy(alpha = 0.2f))
                                .padding(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color(0xfff46859))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "Your Delivery Address", color = LocalTextStyle.current.color.copy(alpha = 0.5f))
                            Text(text = "4517 Washington Ave. Manchester, Kentucky 39495", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xfff46859)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Order Details", fontSize = 20.sp)
                }
            }

        }
    }
}
