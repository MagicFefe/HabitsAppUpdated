package com.swaptech.habitstwo.implofelements.recyclerview

import com.swaptech.data.models.HabitForLocal

interface RecyclerViewClickListener {
    fun onRecyclerViewListClickListener(habit: HabitForLocal, position: Int)
}

