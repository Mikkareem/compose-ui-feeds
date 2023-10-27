package com.techullurgy.composeuisapplication.seatlayout


enum class SeatStatus {
    AVAILABLE, NOT_AVAILABLE, BOOKED, SELECTED;

    companion object {
        internal fun random(): SeatStatus = values().random()
    }
}