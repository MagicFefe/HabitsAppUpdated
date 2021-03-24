package com.swaptech.habitstwo.recyclerview

import com.swaptech.data.models.HabitForLocal

interface RecyclerViewClickListener {
    fun onRecyclerViewListClickListener(data: HabitForLocal, position: Int)
}

