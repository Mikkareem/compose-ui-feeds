package com.techullurgy.composeuisapplication

import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun SpotifyLandingScreenUI() {
    val currentTextStyle = LocalTextStyle.current

    val poppinsTextStyle = remember(currentTextStyle) {
//        currentTextStyle.copy(fontFamily = PoppinsFontFamily, letterSpacing = (-0.75f).sp, color = Color.White, lineHeight = (14).sp)
        currentTextStyle.copy(color = Color.White)
    }

    CompositionLocalProvider(LocalTextStyle provides poppinsTextStyle) {
        SpotifyLandingScreen()
    }
}

@Composable
private fun SpotifyLandingScreen() {
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .padding(8.dp)
    ) {
        TopBar()
        ChipsSection()
        RecentSection()
        Section(
            sectionType = "More Like",
            sectionTitle = "Yuvan Shankar Raja",
            mainSectionItems = listOf(
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Green),
            ),
            backgroundColor = Color.Green,
            modifier = Modifier.padding(top = 8.dp)
        )
        Section(
            sectionType = "More Like",
            sectionTitle = "Yuvan Shankar Raja",
            mainSectionItems = listOf(
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
                MainSectionItem(title = "90s Hits", subtitle = "Ilayaraja", backgroundColor = Color.Magenta),
            ),
            backgroundColor = Color.Magenta,
            modifier = Modifier.padding(top = 8.dp)
        )
        BottomNavigationSection()
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Good evening", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Row {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
private fun ChipsSection() {
    Row(modifier = Modifier.padding(16.dp)) {
        Chip(text = "Music")
        Spacer(modifier = Modifier.width(8.dp))
        Chip(text = "Podcasts & Shows")
    }
}

@Composable
private fun Chip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(45))
            .background(color = Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

@Composable
private fun RecentSection() {
    Column {
        Row {
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            RecentItem(text = "All time Favourites", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RecentItem(text: String, modifier: Modifier = Modifier, imageUrl: Uri = Uri.EMPTY) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .background(color = Color.DarkGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(60.dp)) {
            if(imageUrl == Uri.EMPTY) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Magenta))
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}


@Composable
private fun TopSection(
    type: String,
    title: String,
    backgroundColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = backgroundColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = type.uppercase(), letterSpacing = (1.8f).sp)
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun MainSection(
    mainSectionItems: List<MainSectionItem>
) {
    LazyRow {
        items(mainSectionItems) { item ->
            SectionItem(backgroundColor = item.backgroundColor, title = item.title, subtitle = item.subtitle)
        }
    }
}

@Composable
private fun SectionItem(
    backgroundColor: Color,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
        Text(text = title, fontWeight = FontWeight.Medium)
        Text(text = subtitle, fontWeight = FontWeight.Light, fontSize = 11.sp)
    }
}

@Composable
private fun Section(
    sectionType: String,
    sectionTitle: String,
    backgroundColor: Color,
    mainSectionItems: List<MainSectionItem>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TopSection(sectionType, sectionTitle, backgroundColor)
        MainSection(mainSectionItems)
    }
}

private data class MainSectionItem(
    val title: String,
    val subtitle: String,
    val backgroundColor: Color
)

@Composable
private fun BottomNavigationSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.Green, modifier = Modifier.size(32.dp))
        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
        Icon(imageVector = Icons.Default.List, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
    }
}