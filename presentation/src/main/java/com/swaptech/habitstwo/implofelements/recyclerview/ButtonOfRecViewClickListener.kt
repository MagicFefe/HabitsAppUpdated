package com.swaptech.habitstwo.implofelements.recyclerview

import com.swaptech.data.models.HabitForLocal

interface ButtonOfRecViewClickListener {
    fun onButtonOfRecViewClickListener(habit: HabitForLocal, position: Int)
}