package com.techullurgy.composeuisapplication.seatlayout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.composeuisapplication.ZoomableContainer


@Preview
@Composable
fun SeatLayout(
    seats: List<SeatContent> = seatContents
) {

    var height by remember { mutableStateOf(0.dp) }

    ZoomableContainer {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.Black)
        ) {

            val groupedSeats = seats.groupBy { it.row }

            val maxSeatInRow = groupedSeats.maxOf { it.value.size }

            val seatSize = Size(size.width / maxSeatInRow, size.width / maxSeatInRow)

            if(height == 0.dp) {
                height = (groupedSeats.size * seatSize.height).toDp()
            }

            repeat(groupedSeats.size) { row ->
                groupedSeats[row]?.forEachIndexed { index, value ->
                    when(value.seatOccupancy) {
                        is Seat -> seatView(
                            seat = value.seatOccupancy,
                            seatSize = seatSize,
                            at = Offset(
                                x = index * seatSize.width,
                                y = row * seatSize.height
                            )
                        )
                        is Vacuum -> vacuumView(vacuumSize = seatSize)
                    }
                }
            }
        }
    }
}