package com.techullurgy.composeuisapplication.messageappui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.composeuisapplication.R
import com.techullurgy.composeuisapplication.math.map
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val grayColor = Color(0xff8a95b3)

@Composable
fun ChatDetailsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(12.dp))
        DateText()
        Spacer(modifier = Modifier.height(8.dp))
        MessagesSection(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            messageDetails = listOf(
                MessageDetail(
                    ownerType = OwnerType.Me,
                    message = "Hi, How are you?",
                    time = "Just now"
                ),
                MessageDetail(
                    ownerType = OwnerType.Other,
                    message = "Hi, How are you?, Please recollect your knowledge to the next level",
                    time = "Just now"
                ),
                MessageDetail(
                    ownerType = OwnerType.Other,
                    message = "Hi, How are you?",
                    time = "Just now"
                ),
                MessageDetail(
                    ownerType = OwnerType.Me,
                    message = "Hi, How are you?",
                    time = "Just now"
                ),
                MessageDetail(
                    ownerType = OwnerType.Me,
                    message = "Hi, How are you?",
                    time = "Just now"
                ),
                MessageDetail(
                    ownerType = OwnerType.Me,
                    message = "please",
                    time = "Just now"
                )
            )
        )
        BottomBar(
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun MessagesSection(
    messageDetails: List<MessageDetail>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        for(i in messageDetails.indices) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = when(messageDetails[i].ownerType) {
                    is OwnerType.Me -> Arrangement.End
                    is OwnerType.Other -> Arrangement.Start
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                val canShowProfilePicture = (
                        (i == messageDetails.size - 1) ||
                                ((i+1) < messageDetails.size &&
                                        messageDetails[i].ownerType != messageDetails[i+1].ownerType)
                        )

                when(messageDetails[i].ownerType) {
                    is OwnerType.Me -> {
                        MeMessage(
                            canShowProfilePicture = canShowProfilePicture,
                            messageDetail = messageDetails[i]
                        )
                    }
                    is OwnerType.Other -> {
                        OtherMessage(
                            canShowProfilePicture = canShowProfilePicture,
                            messageDetail = messageDetails[i]
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            val alpha = 0.4f
            ProfilePicture(isOnline = true, color = Color.Magenta, alpha = alpha)
            Spacer(modifier = Modifier.width(8.dp))
            TypingIndicator(alpha = alpha)
        }
    }
}

@Composable
private fun MeMessage(
    canShowProfilePicture: Boolean,
    messageDetail: MessageDetail,
    modifier: Modifier = Modifier
) {
    Row(modifier =  modifier) {
        Spacer(modifier = Modifier.width(50.dp))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 35,
                            topEndPercent = 35,
                            bottomEndPercent = if (canShowProfilePicture) 0 else 35,
                            bottomStartPercent = 35
                        )
                    )
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(text = messageDetail.message, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        if(canShowProfilePicture) {
            ProfilePicture(isOnline = true)
        } else {
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}

@Composable
private fun OtherMessage(
    canShowProfilePicture: Boolean,
    messageDetail: MessageDetail,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        if(canShowProfilePicture) {
            ProfilePicture(isOnline = true, color = Color.Magenta)
        } else {
            Spacer(modifier = Modifier.width(50.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 35,
                            topEndPercent = 35,
                            bottomEndPercent = 35,
                            bottomStartPercent = if (canShowProfilePicture) 0 else 35
                        )
                    )
                    .background(grayColor)
                    .padding(16.dp)
            ) {
                Text(text = messageDetail.message)
            }
        }
        Spacer(modifier = Modifier.width(50.dp))
    }
}

@Composable
private fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = object: Arrangement.Horizontal {
            override fun Density.arrange(
                totalSize: Int,
                sizes: IntArray,
                layoutDirection: LayoutDirection,
                outPositions: IntArray
            ) {
                outPositions[1] = 0
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0, 0, 35, 35))
            .background(grayColor)
            .padding(vertical = 16.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.size(48.dp))
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfilePicture(isOnline = true, color = Color.Magenta)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Jennie Davis", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProfilePicture(
    isOnline: Boolean,
    color: Color = Color.Black,
    alpha: Float = 1f
) {
    Box {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = alpha))
        )
        AnimatedVisibility(visible = isOnline, modifier = Modifier.align(Alignment.BottomEnd)) {
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(Color.Green.copy(alpha = alpha))
                    .border(2.dp, Color.White, CircleShape)
            )
        }
    }
}

@Composable
private fun DateText(
    dateStr: String = "12 MARCH 2023"
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = dateStr, color = grayColor, textAlign = TextAlign.Center)
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        var messageValue by remember { mutableStateOf(" e") }
        TextField(
            value = messageValue,
            onValueChange = { messageValue = it },
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Enter Message...")
            },
            trailingIcon = {
                Crossfade(targetState = messageValue.isNotBlank(), label = "") {
                    if(it) {
                        IconButton(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Black)
                                .padding(4.dp)
                                .graphicsLayer { rotationZ = -45f },
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = Color.White)
                        }
                    } else {
                        Row(modifier = Modifier.padding(end = 16.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_mic_24),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_attach_file_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .graphicsLayer { rotationZ = 45f }
                            )
                        }
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = grayColor,
                focusedContainerColor = grayColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TypingIndicator(
    totalDots: Int = 3,
    alpha: Float = 1f
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(35))
            .background(grayColor.copy(alpha = alpha))
            .padding(16.dp)
    ) {
        val animationProgresses = remember { List(totalDots) { Animatable(0f) } }

        LaunchedEffect(key1 = Unit) {
            launch {
                while (true) {
                    animationProgresses.forEach {
                        launch {
                            val result = it.animateTo(1f)
                            if(result.endReason == AnimationEndReason.Finished) {
                                it.animateTo(0f)
                            }
                        }
                        delay(100)
                    }
                    delay(700)
                }
            }
        }

        Row(
            modifier = Modifier.height(25.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(totalDots) {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = 0,
                                y = map(
                                    FastOutSlowInEasing.transform(animationProgresses[it].value),
                                    0f,
                                    1f,
                                    0f,
                                    -10.dp.toPx()
                                ).toInt()
                            )
                        }
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = alpha))
                )
            }
        }
    }
}

private sealed interface OwnerType {
    object Me: OwnerType
    object Other: OwnerType
}

private data class MessageDetail(
    val ownerType: OwnerType,
    val message: String,
    val time: String
)

@Preview
@Composable
fun ChatDetailsScreenPreview() {
    ChatDetailsScreen()
}