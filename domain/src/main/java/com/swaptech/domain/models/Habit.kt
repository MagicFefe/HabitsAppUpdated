package com.swaptech.domain.models

data class Habit(
    var color: Int = 1,
    var count: Int = 1,
    var date: Int = 1,
    var description: String,
    val doneDates: MutableList<Int> = mutableListOf(0),
    var frequency: Int,
    var priority: Int,
    var title: String,
    var type: Int,
    var uid: String = "",
)
