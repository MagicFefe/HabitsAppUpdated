package com.swaptech.habitstwo.recyclerview

import com.swaptech.habitstwo.model.RecItem

interface RecyclerViewClickListener {
    fun onRecyclerViewListClickListener(data: RecItem, position: Int)
}

