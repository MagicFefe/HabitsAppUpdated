package com.swaptech.habitstwo

import com.swaptech.data.models.HabitForLocal


class HabitsComparator {
    fun compare(habit: HabitForLocal, habit2: HabitForLocal): Boolean {
        return (habit.title == habit2.title
                && habit.type == habit2.type
                && (habit.uid == habit2.uid || habit.uid != habit2.uid)
                && habit.color == habit2.color
                && habit.count == habit2.count
                && habit.date == habit2.date
                && habit.description == habit2.description
                && habit.frequency == habit2.frequency
                && habit.priority == habit2.priority
                && (habit.doneDates == habit2.doneDates || habit.doneDates != habit2.doneDates))
    }
}