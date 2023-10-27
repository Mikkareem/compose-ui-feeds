package com.techullurgy.composeuisapplication.seatlayout

import kotlin.random.Random
import kotlin.reflect.KClass

sealed interface SeatOccupancy {
    companion object {
        internal fun randomSeatAt(row: Int, col: Int): SeatOccupancy {
            return if(Random.nextFloat() > 0.7f) {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            } else {
                Seat(
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            }
        }

        internal fun randomSeatAt(row: Int, col: Int, occupancy: String): SeatOccupancy {
            return if(occupancy == "V") {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            } else {
                Seat(
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            }
        }

        internal fun randomSeatAt(row: Int, col: Int, occupancy: KClass<out SeatOccupancy>): SeatOccupancy {
            return if(occupancy == Seat::class) {
                Seat(
                    point = Point(row = row, col = col),
                    seatStatus = SeatStatus.random(),
                    seatPrice = Random.nextDouble(120.0, 300.0),
                    seatCategory = SeatCategory.random()
                )
            } else {
                Vacuum(
                    point = Point(row = row, col = col)
                )
            }
        }
    }
}

data class Vacuum(val point: Point): SeatOccupancy

data class Seat(
    val point: Point,
    val seatStatus: SeatStatus,
    val seatPrice: Double,
    val seatCategory: SeatCategory
): SeatOccupancy
