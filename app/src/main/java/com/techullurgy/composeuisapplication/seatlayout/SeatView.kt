package com.techullurgy.composeuisapplication.seatlayout

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

internal fun DrawScope.seatView(
    seat: Seat,
    seatSize: Size,
    at: Offset
) {
    val handleSize = seatSize.width * 0.1f
    val padding = seatSize.width * 0.05f

    val seatColor = when(seat.seatCategory) {
        SeatCategory.FIRST_CLASS -> { Color.White }
        SeatCategory.SECOND_CLASS -> { Color.Yellow }
        SeatCategory.BALCONY -> { Color.Red }
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

    drawPath(
        path = seatPath,
        brush = Brush.verticalGradient(
            colors = listOf(seatColor, seatColor.copy(alpha = 0.3f)),
            startY = at.y,
            endY = at.y + seatSize.height
        )
    )

    // Seat Handles
    drawRoundRect(
        color = seatColor,
        topLeft = Offset(at.x + padding, at.y + seatSize.height * 0.5f),
        size = Size(handleSize, seatSize.height - seatSize.height * 0.5f),
    )

    drawRoundRect(
        color = seatColor,
        topLeft = Offset(at.x + seatSize.width - padding - handleSize, at.y + seatSize.height * 0.5f),
        size = Size(handleSize, seatSize.height - seatSize.height * 0.5f),
    )

    drawRoundRect(
        color = seatColor,
        topLeft = Offset(at.x + padding, at.y + seatSize.height - handleSize),
        size = Size(seatSize.width - padding - handleSize/2, handleSize),
    )
}