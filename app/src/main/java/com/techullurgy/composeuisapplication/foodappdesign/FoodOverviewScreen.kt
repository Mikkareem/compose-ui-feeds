package com.techullurgy.composeuisapplication.foodappdesign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.R

@Preview
@Composable
fun FoodOverviewScreen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color(0xfff6f7f7))
            .padding(16.dp)
    ) {
        TopBarView()
        Spacer(modifier = Modifier.height(16.dp))
        SearchBarView()
        Spacer(modifier = Modifier.height(16.dp))
        SpecialDealsCard()
        Spacer(modifier = Modifier.height(16.dp))
        PopularFoodsSection()
        Spacer(modifier = Modifier.height(16.dp))
        HistoryFoodsSection()
    }
}

@Composable
private fun TopBarView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sample_profile2),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Hi, Nanas")
            Text(text = "What do you want to eat?", fontSize = 18.sp)
        }
        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, modifier = Modifier.size(36.dp))
    }
}

@Composable
private fun SearchBarView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(50),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = "Search for food")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Outlined.List, contentDescription = null, modifier = Modifier.size(28.dp), tint = Color.White)
        }
    }
}

@Composable
private fun SpecialDealsCard() {
        Layout(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xff3f434b)),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ProvideTextStyle(value = LocalTextStyle.current.copy(color = Color.White)) {
                        Text(text = "Special Deals")
                        Text(text = "50% OFF", fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                        Text(text = "and get free delivery", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xfff46859)
                        )
                    ) {
                        Text(text = "Order now")
                    }
                }
                Box(
                    Modifier
                        .aspectRatio(1f)
//                        .clip(CircleShape)
//                        .background(Color.Yellow)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.food2),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        ) { measurables, constraints ->
            val leftConstraints = constraints.copy(maxWidth = constraints.maxWidth / 2)
            val leftPlaceable = measurables[0].measure(leftConstraints)
            val maxHeight = leftPlaceable.height
            val rightConstraints = leftConstraints.copy(maxHeight = maxHeight)
            val rightPlaceable = measurables[1].measure(rightConstraints)
            layout(constraints.maxWidth, maxHeight) {
                leftPlaceable.place(x = 0, y = 0)
                rightPlaceable.place(x = leftConstraints.maxWidth, y = 0)
            }
        }
}

@Composable
private fun PopularFoodsSection() {
    val popularFoods = listOf(
        FoodCardDetail(title = "Ebi Sushi", rating = 4.9, totalReviews = 109, price = 4.99),
        FoodCardDetail(title = "Sake Sushi", rating = 3.6, totalReviews = 87, price = 2.88),
        FoodCardDetail(title = "Rato Kachap", rating = 3.55, totalReviews = 69, price = 8.14),
        FoodCardDetail(title = "Guli Paind", rating = 2.27, totalReviews = 179, price = 6.73),
    )
    Column {
        OverviewSection(title = "Popular Foods")
        Spacer(modifier = Modifier.height(16.dp))
        FoodCardRow(foodCardDetails = popularFoods)
    }
}

@Composable
private fun HistoryFoodsSection() {
    val history = listOf(
        FoodCardDetail(title = "Gar Repto", rating = 4.9, totalReviews = 109, price = 4.99),
        FoodCardDetail(title = "Ruji Tawan", rating = 3.6, totalReviews = 87, price = 2.88),
        FoodCardDetail(title = "Kesp Rota", rating = 3.55, totalReviews = 69, price = 8.14),
        FoodCardDetail(title = "loet poty", rating = 2.27, totalReviews = 179, price = 6.73),
    )
    Column {
        OverviewSection(title = "Based on your history")
        Spacer(modifier = Modifier.height(16.dp))
        FoodCardRow(foodCardDetails = history)
    }
}

@Composable
private fun FoodCard(
    foodCardDetail: FoodCardDetail
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
//            .background(Color(0xfff46859))
        ) {
            Image(
                painter = painterResource(id = R.drawable.food2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clipToBounds()
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(text = foodCardDetail.title, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${foodCardDetail.rating}(${foodCardDetail.totalReviews} reviews)", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${foodCardDetail.price}", fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xfff46859))
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun OverviewSection(
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 28.sp, modifier = Modifier.weight(1f))
        Text(text = "See all")
    }
}

@Composable
private fun FoodCardRow(
    foodCardDetails: List<FoodCardDetail>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(foodCardDetails) {
            FoodCard(it)
        }
    }
}

private data class FoodCardDetail(
    val title: String,
    val rating: Double,
    val totalReviews: Int,
    val price: Double
)