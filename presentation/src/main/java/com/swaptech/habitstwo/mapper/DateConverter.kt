package com.swaptech.habitstwo.mapper

class DateConverter {
    fun convertDayAndMonth(day: Int, month: Int): Int {
        val dayString = if(day < 10) {
            "0$day"
        } else {
            "$day"
        }

        val monthString = if(month < 10) {
            "0$month"
        } else {
            "$month"
        }

        val dayMonth = "$dayString$monthString".toInt()

        return dayMonth
    }
}