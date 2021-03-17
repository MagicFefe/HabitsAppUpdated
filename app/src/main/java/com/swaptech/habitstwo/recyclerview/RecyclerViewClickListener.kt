package com.swaptech.habitstwo.recyclerview

import com.swaptech.habitstwo.model.HabitForLocal

interface RecyclerViewClickListener {
    fun onRecyclerViewListClickListener(data: HabitForLocal, position: Int)
}

