package com.techullurgy.composeuisapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import java.lang.Integer.max

internal val MinTextFieldHeight = 50.dp
internal val DefaultTextFieldFontSize = 20.sp
internal val DefaultTextFieldInnerPadding = 18.dp

@Composable
private fun CustomTextField(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = DefaultTextFieldFontSize,
    color: Color = Color.Black,
    textStyle: TextStyle = LocalTextStyle.current.copy(fontSize = fontSize, color = color),
    maxLines: Int = 4,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(40f),
    innerPadding: PaddingValues = PaddingValues(all = DefaultTextFieldInnerPadding),
    border: BorderStroke = BorderStroke(width = 3.dp, brush = SolidColor(Color.Magenta)),
    containerBrush: Brush = Brush.horizontalGradient(
        colors = listOf(Color.Magenta, Color(red = 200, green = 100, blue = 0))
    ),
    focusRequester: FocusRequester? = null,
    onFocusChanged: (FocusState) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    var canFocusRequestExplicit: Boolean by remember { mutableStateOf(focusRequester?.let { true } ?: false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = textStyle,
        maxLines = maxLines,
        modifier = modifier
            .then(
                focusRequester?.let {
                    Modifier.focusRequester(it)
                } ?: Modifier
            )
            .then(
                onFocusChanged.let { listener ->
                    Modifier.onFocusChanged {
                        canFocusRequestExplicit = !it.isFocused
                        listener(it)
                    }
                }
            ),
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    ) { innerTextField ->
        CustomTextFieldLayout(
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            innerTextField = innerTextField,
            canFocusRequestExplicit = canFocusRequestExplicit,
            onFocus = {
                focusRequester?.requestFocus()
            },
            modifier = Modifier
                .heightIn(min = MinTextFieldHeight)
                .clip(shape)
                .background(brush = containerBrush)
                .border(shape = shape, border = border)
                .padding(paddingValues = innerPadding)
        )
    }
}

@Composable
private fun CustomTextFieldLayout(
    modifier: Modifier = Modifier,
    canFocusRequestExplicit: Boolean = false,
    onFocus: (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    innerTextField: @Composable () -> Unit = {}
) {
    val leadingIconWithId: @Composable (() -> Unit)? = leadingIcon?.let {
        @Composable {
            Box(modifier = Modifier.layoutId("leadingIcon")) {
                it()
            }
        }
    }

    val trailingIconWithId: @Composable (() -> Unit)? = trailingIcon?.let {
        @Composable {
            Box(modifier = Modifier.layoutId("trailingIcon")) {
                it()
            }
        }
    }

    val innerTextFieldWithId: @Composable () -> Unit = innerTextField.let {
        @Composable {
            Box(modifier = Modifier.layoutId("innerTextField")) {
                it()
            }
        }
    }

    val focusHider: @Composable (() -> Unit)? = if(canFocusRequestExplicit) {
        @Composable {
            Box(
                modifier = Modifier
                    .layoutId("focusHider")
                    .background(color = Color.Transparent)
                    .clickable { onFocus?.let { it() } }
            )
        }
    } else null

    val content: @Composable () -> Unit = @Composable {
        leadingIconWithId?.let { it() }
        innerTextFieldWithId()
        focusHider?.let { it() }
        trailingIconWithId?.let { it() }
    }

    CustomTextFieldLayout(modifier = modifier, content = content)
}

@Composable
private fun CustomTextFieldLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = CustomTextFieldLayoutMeasurePolicy()
    )
}

private class CustomTextFieldLayoutMeasurePolicy: MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val trailingIconsPlaceable = measurables.find { it.layoutId == "trailingIcon" }?.measure(looseConstraints)
        val leadingIconsPlaceable = measurables.find { it.layoutId == "leadingIcon" }?.measure(looseConstraints)

        val occupiedSpaceHorizontally = trailingIconsPlaceable.widthOrZero() + leadingIconsPlaceable.widthOrZero()

        val innerTextFieldConstraints = constraints.offset(horizontal = -occupiedSpaceHorizontally)

        val innerTextFieldPlaceable = measurables.find { it.layoutId == "innerTextField" }?.measure(innerTextFieldConstraints)!!

        val totalWidth = leadingIconsPlaceable.widthOrZero() + innerTextFieldPlaceable.width + trailingIconsPlaceable.widthOrZero()
        val totalHeight = max(innerTextFieldPlaceable.height,
                                max(leadingIconsPlaceable.heightOrZero(), trailingIconsPlaceable.heightOrZero()))

        val focusHiderPlaceable = measurables.find { it.layoutId == "focusHider" }?.measure(
            Constraints(
                minWidth = innerTextFieldPlaceable.width,
                maxWidth = innerTextFieldPlaceable.width,
                minHeight = innerTextFieldPlaceable.height,
                maxHeight = innerTextFieldPlaceable.height
            )
        )

        return layout(totalWidth,totalHeight) {
            var currentPositionX = 0

            leadingIconsPlaceable?.let {
                it.place(currentPositionX, totalHeight / 2 - it.height / 2)
                currentPositionX += it.width
            }

            innerTextFieldPlaceable.let {
                it.place(currentPositionX, 0)
                focusHiderPlaceable?.place(currentPositionX, 0)
                currentPositionX += it.width
            }

            trailingIconsPlaceable?.let {
                it.place(currentPositionX, totalHeight / 2 - it.height / 2)
                currentPositionX += it.width
            }
        }
    }
}

private fun Placeable?.widthOrZero(): Int = this?.width ?: 0
private fun Placeable?.heightOrZero(): Int = this?.height ?: 0

@Preview
@Composable
fun Test() {
    var text by remember {
        val t1 = "Hello World".padEnd(280, 'a')
        mutableStateOf(TextFieldValue(t1))
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column {
        CustomTextField(
            value = text,
            onValueChanged = { text = it },
            trailingIcon = {
                Row {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(40.dp))
                }
            },
            focusRequester = focusRequester,
            onFocusChanged = {
                if(!it.isFocused) {
                    text = text.copy(selection = TextRange(text.text.length))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(onClick = { focusRequester.requestFocus() }) {
            Text(text = "Request Focus")
        }

        Button(onClick = { focusManager.clearFocus() }) {
            Text(text = "Leave Focus")
        }
    }
}