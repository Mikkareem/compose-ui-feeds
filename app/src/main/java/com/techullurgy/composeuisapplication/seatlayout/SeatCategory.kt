package com.techullurgy.composeuisapplication.seatlayout

enum class SeatCategory {
    FIRST_CLASS, SECOND_CLASS, BALCONY;

    companion object {
        internal fun random(): SeatCategory = values().random()
    }
}