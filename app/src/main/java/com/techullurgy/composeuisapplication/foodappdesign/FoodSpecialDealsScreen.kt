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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.R

@Preview
@Composable
fun FoodSpecialDealsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff6f7f7))
                .padding(16.dp)
        ) {
            TopBar()
            Spacer(modifier = Modifier.height(100.dp))
            FoodSpecialDealsMainSection()
        }
        BottomSection(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart))
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

    val cartItems = listOf(
        CartItem(itemName = "Sake Sushi", itemCount = 2, originalPrice = 9.89, discountedPrice = 4.99),
        CartItem(itemName = "Ebi Sushi", itemCount = 1, originalPrice = 2.99, discountedPrice = 1.45)
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color(0xff3f434b))
            .padding(16.dp)
    ) {
        ProvideTextStyle(value = LocalTextStyle.current.copy(color = Color.White)) {
            Text(text = "Order List", fontSize = 24.sp)
            cartItems.forEach {
                CartItemView(item = it)
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xfff46859)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Pay $6.44")
            }
        }
    }
}

@Composable
private fun CartItemView(
    item: CartItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.food2),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = buildAnnotatedString {
                    append(item.itemName)
                    append("  ")
                    withStyle(SpanStyle(fontSize = 12.sp, color = LocalTextStyle.current.color.copy(alpha = 0.6f))) {
                        append("x${item.itemCount}")
                    }
                }
            )
            Row {
                StrikeText(text = "$${item.originalPrice}")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$${item.discountedPrice}")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 24.sp, baselineShift = BaselineShift(0.2f))) {
                        append("--")
                    }
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.drawBehind {
                    drawCircle(color = Color.White, radius = size.width, style = Stroke(width = 1.dp.toPx()))
                }
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = "${item.itemCount}", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = "+",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.drawBehind {
                    drawCircle(color = Color.White, radius = size.width, style = Stroke(width = 1.dp.toPx()))
                }
            )
        }
    }
}

@Composable
private fun StrikeText(text: String) {
    val strikeColor = LocalTextStyle.current.color
    Text(
        text = text,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = strikeColor,
                    start = Offset(x = size.width, y = 2.dp.toPx()),
                    end = Offset(x = 0f, y = size.height - 2.dp.toPx()),
                    strokeWidth = 2.dp.toPx()
                )
            }
    )
}

private data class CartItem(
    val itemName: String,
    val itemCount: Int,
    val originalPrice: Double,
    val discountedPrice: Double
)