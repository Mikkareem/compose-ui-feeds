package com.techullurgy.composeuisapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun OptionsPicker(
    options: List<String>,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Please Select one option",
    accentColor: Color = Color.Blue,
    fontSize: TextUnit = MaterialTheme.typography.labelLarge.fontSize
) {
    require(options.contains(value) || value.isEmpty())

    var optionShow by remember {
        mutableStateOf(false)
    }

    Text(
        text = value.ifEmpty { "Select" },
        color = accentColor,
        fontSize = fontSize,
        modifier = modifier.clickable { optionShow = !optionShow }
    )

    if(optionShow) {
        OptionsPickerDialog(
            title = title,
            options = options,
            onValueChange = onValueChange,
            onDismissRequest = { optionShow = !optionShow }
        )
    }
}

@Composable
private fun OptionsPickerDialog(
    title: String,
    options: List<String>,
    onDismissRequest: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(max = 300.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title)
            LazyColumn {
                items(options) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onValueChange(it)
                                onDismissRequest()
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it,
                            color = Color.Blue,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Divider()
                }
            }
        }
    }
}

@Preview
@Composable
fun OptionsPickerPreview() {

    var selectedValue by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            val colors = listOf("Red", "Blue", "Green", "Orange", "Yellow", "Pink", "Violet", "Indigo", "Magenta")
            Text(text = "Color: ")
            Spacer(modifier = Modifier.width(30.dp))
            OptionsPicker(
                options = colors,
                value = selectedValue,
                onValueChange = { selectedValue = it },
                title = "Please select one Color"
            )
        }
    }
}