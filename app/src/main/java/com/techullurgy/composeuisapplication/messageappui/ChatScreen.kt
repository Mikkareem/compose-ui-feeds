package com.techullurgy.composeuisapplication.messageappui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val grayColor = Color(0xff8a95b3)

@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(color = Color.Black))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Messages", textAlign = TextAlign.Center, fontSize = 20.sp, modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = grayColor)
        }
    }
}

@Composable
private fun TopNavigationBar() {
    val topNavigationItems = listOf("Recent", "Favorites", "Work", "All")
    val selectedIndex = 0

    Row {
        topNavigationItems.forEachIndexed { index, it ->
            TopNavigationItem(
                name = it,
                isSelected = selectedIndex == index,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TopNavigationItem(
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = if (isSelected) Color.Black else grayColor,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ChatScreen() {

    val chatList = remember {
        listOf(
            MessageItemState(title = "Jennie Davis", subtitle = "Hi!", time = "1m ago", isOnline = true, newMessagesCount = 2),
            MessageItemState(title = "Lucy Brown", subtitle = "Good point, dear)", time = "35m ago"),
            MessageItemState(title = "James Wilson", subtitle = "Ok, Let's be in touch.", time = "1h ago"),
            MessageItemState(title = "Janine Taylor", subtitle = "I'll be waiting for you there.", time = "3h ago"),
            MessageItemState(title = "Dan Miller", subtitle = "Ok.", time = "5h ago"),
            MessageItemState(title = "Sam Tinko", subtitle = "Oh, thanks", time = "1d ago")
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        TopAppBar()
        TopNavigationBar()
        Column(modifier = Modifier.weight(1f)) {
            chatList.forEach {
                MessageItem(messageItemState = it)
            }
            EncryptionMessage()
        }
        BottomBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageItem(
    messageItemState: MessageItemState
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            ProfilePicture(messageItemState.isOnline)
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = messageItemState.title, fontSize = 18.sp)
                Text(text = messageItemState.subtitle, color = grayColor, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = messageItemState.time, color = grayColor)
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedVisibility(visible = messageItemState.newMessagesCount > 0) {
                    Badge(
                        containerColor = Color.Green
                    ) {
                        Text(text = messageItemState.newMessagesCount.toString(), fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

private data class MessageItemState(
    val title: String,
    val subtitle: String,
    val time: String,
    val isOnline: Boolean = false,
    val newMessagesCount: Int = 0,
    val profilePicUrl: String? = null
)

@Composable
private fun ProfilePicture(
    isOnline: Boolean,
) {
    Box {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Black)
        )
        AnimatedVisibility(visible = isOnline, modifier = Modifier.align(Alignment.BottomEnd)) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(2.dp, Color.White, CircleShape)
            )
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(45, 45, 0, 0))
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(imageVector = Icons.Default.MailOutline, contentDescription = null, tint = Color.White)
        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = grayColor)
        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = grayColor)
        Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = grayColor)
    }
}

@Composable
private fun EncryptionMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The messages are protected by the end-to-end encryption",
                fontSize = 16.sp,
                color = grayColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen()
}