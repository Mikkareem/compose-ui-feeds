package com.techullurgy.composeuisapplication.bookableseatlayout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.seatlayout.Point
import com.techullurgy.composeuisapplication.seatlayout.Seat
import com.techullurgy.composeuisapplication.seatlayout.SeatCategory
import com.techullurgy.composeuisapplication.seatlayout.SeatStatus

internal fun DrawScope.seatView(
    seat: Seat,
    seatSize: Size,
    at: Offset
) {
    val handleSize = seatSize.width * 0.1f
    val padding = seatSize.width * 0.05f

    val color = when(seat.seatCategory) {
        SeatCategory.FIRST_CLASS -> Color.White
        SeatCategory.SECOND_CLASS -> Color.Yellow
        SeatCategory.BALCONY -> Color.Red
    }

    val seatStyle = when(seat.seatStatus) {
        SeatStatus.AVAILABLE, SeatStatus.NOT_AVAILABLE -> Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        SeatStatus.BOOKED, SeatStatus.SELECTED -> Fill
    }

    val seatPath = Path().apply {
        moveTo(at.x + padding + handleSize/2, at.y + seatSize.height)
        lineTo(at.x + padding + handleSize/2, at.y + seatSize.height * 0.3f)
        quadraticBezierTo(
            at.x + seatSize.width / 2, at.y + seatSize.height * 0.05f,
            at.x + seatSize.width - (padding + handleSize/2), at.y + seatSize.height * .3f
        )
        lineTo(at.x + seatSize.width - (padding + handleSize / 2), at.y + seatSize.height)
        close()
    }

    val seatColor = when(seat.seatStatus) {
        SeatStatus.BOOKED -> Color.Blue
        SeatStatus.SELECTED -> Color.Magenta
        else -> color
    }

    drawPath(
        path = seatPath,
        color = seatColor,
        style = seatStyle
    )

    drawHandles(
        color = seatColor,
        seatSize = seatSize,
        style = seatStyle,
        at = at,
        padding = padding,
        handleSize = handleSize
    )

    if(seat.seatStatus == SeatStatus.BOOKED || seat.seatStatus == SeatStatus.SELECTED) {
        drawHandles(
            color = Color.Black.copy(alpha = 0.6f),
            seatSize = seatSize,
            style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
            at = at,
            padding = padding,
            handleSize = handleSize
        )
    }
}

private fun DrawScope.drawHandles(
    color: Color,
    seatSize: Size,
    style: DrawStyle,
    at: Offset,
    padding: Float,
    handleSize: Float
) {
    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + padding, at.y + seatSize.height * 0.5f),
        size = Size(handleSize, seatSize.height - seatSize.height * 0.5f),
        style = style
    )

    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + seatSize.width - padding - handleSize, at.y + seatSize.height * 0.5f),
        size = Size(handleSize, seatSize.height - seatSize.height * 0.5f),
        style = style
    )

    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + padding + handleSize, at.y + seatSize.height - handleSize),
        size = Size(seatSize.width - 2*padding - 2*handleSize, handleSize),
        style = style
    )
}

@Preview
@Composable
fun SeatView() {
    Canvas(modifier = Modifier.size(24.dp)) {
        seatView(
            seat = Seat(
                point = Point(0, 0),
                seatStatus = SeatStatus.AVAILABLE,
                seatPrice = 120.0,
                seatCategory = SeatCategory.FIRST_CLASS
            ),
            seatSize = size,
            at = Offset.Zero
        )
    }
}