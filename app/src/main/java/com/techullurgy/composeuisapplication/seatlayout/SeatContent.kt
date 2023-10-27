package com.techullurgy.composeuisapplication.seatlayout

data class SeatContent(
    val row: Int,
    val column: Int,
    val rowIdentifier: String,
    val colIdentifier: String,
    val seatOccupancy: SeatOccupancy
)

val seatContents = mutableListOf<SeatContent>().apply {
    val identifiers = "VTSRQPONMLKJIHGFEDCBA"

    repeat(20) { row ->
        repeat(35) { col ->
            val occupancy = if(row == 4) {
                Vacuum::class
            } else if(col == 4 || col == 32) {
                Vacuum::class
            } else {
                Seat::class
            }

            add(
                SeatContent(
                    row = row,
                    column = col,
                    rowIdentifier = "${identifiers[row]}",
                    colIdentifier = "$col",
                    seatOccupancy = SeatOccupancy.randomSeatAt(row, col, occupancy)
                )
            )
        }
    }
}.toList()

fun main() {
    seatContents.forEach {
        println(it.seatOccupancy)
    }
}