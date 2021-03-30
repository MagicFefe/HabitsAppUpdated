package com.swaptech.habitstwo.mapper

import java.lang.Exception
import java.util.*

//Format of date: dd-mm
class DateConverter {
    private companion object {
        const val COUNT_OF_MONTHS = 12
        const val MAX_DAYS_FOR_ODD_MONTHS = 31
        const val MAX_DAYS_FOR_EVEN_MONTHS = 30
    }

    private val calendar = Calendar.getInstance()
    private val currentYear = calendar.get(Calendar.YEAR)
    private val februaryDays = if (currentYear % 400 == 0
            || (currentYear % 4 == 0 && currentYear % 100 == 0)) {
        29
    } else {
        28
    }
    private val oddMonths = listOf(1, 3, 5, 7, 8, 10, 12)
    private val evenMonths = listOf(4, 6, 9, 11)
    private val currentMonth = calendar.get(Calendar.MONTH) + 1

    fun convertDayAndMonth(day: Int, month: Int): Int {
        val dayString = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }

        val monthString = if (month < 10) {
            "0$month"
        } else {
            "$month"
        }

        return "$dayString$monthString".toInt()
    }


    fun generateEndDate(day: Int, month: Int, day2: Int, month2: Int): Int {
        if (day > MAX_DAYS_FOR_ODD_MONTHS || day2 > MAX_DAYS_FOR_ODD_MONTHS || month > COUNT_OF_MONTHS || month2 > COUNT_OF_MONTHS) {
            throw Exception("INCORRECT DATA")
        } else {

            var endMonth = if (month + month2 > COUNT_OF_MONTHS) {
                month + month2 - COUNT_OF_MONTHS
            } else {
                month + month2
            }
            val uncheckedEndDay = day + day2
            val endDay: Int
            if (oddMonths.contains(endMonth)) {
                if (uncheckedEndDay > MAX_DAYS_FOR_ODD_MONTHS) {
                    val dayDiff = uncheckedEndDay - MAX_DAYS_FOR_ODD_MONTHS
                    endDay = dayDiff
                    endMonth += 1
                } else {
                    endDay = uncheckedEndDay
                }
            } else if (evenMonths.contains(endMonth)) {
                if (uncheckedEndDay > MAX_DAYS_FOR_EVEN_MONTHS) {
                    val dayDiff = uncheckedEndDay - MAX_DAYS_FOR_EVEN_MONTHS
                    endDay = dayDiff
                    endMonth += 1
                } else {
                    endDay = uncheckedEndDay
                }
            } else {
                if (uncheckedEndDay > februaryDays) {
                    val dayDiff = uncheckedEndDay - februaryDays
                    endDay = dayDiff
                    endMonth += 1
                } else {
                    endDay = uncheckedEndDay
                }
            }

            return convertDayAndMonth(endDay, endMonth)
        }
    }

    fun convertDaysToMonths(days: Int): Int {
        return when {
            oddMonths.contains(currentMonth) -> {
                days / MAX_DAYS_FOR_ODD_MONTHS

            }
            evenMonths.contains(currentMonth) -> {
                days / MAX_DAYS_FOR_EVEN_MONTHS
            }
            else -> {
                days / februaryDays
            }
        }
    }

    fun convertMonthsToDays(months: Int): Int {
        return when {
            oddMonths.contains(currentMonth) -> {
                months * MAX_DAYS_FOR_ODD_MONTHS
            }
            evenMonths.contains(currentMonth) -> {
                months * MAX_DAYS_FOR_EVEN_MONTHS
            }
            else -> {
                months * februaryDays
            }
        }
    }
}