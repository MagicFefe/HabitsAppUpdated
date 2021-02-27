package com.swaptech.habitstwo.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem

class Adapter(
        var items: MutableList<RecItem>,
        val context: Context,
        val clickListener: RecyclerViewClickListener
)
    : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var color = 0
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.title_habits_item)
        var description = itemView.findViewById<TextView>(R.id.description_habits_item)
        var priority = itemView.findViewById<TextView>(R.id.priority_habits_item)
        var typeOfHabit = itemView.findViewById<TextView>(R.id.type_habits_item)
        var periodicity = itemView.findViewById<TextView>(R.id.periodicity_habits_item)


        fun bind(recItem: RecItem, context: Context) {
            name.text = recItem.name
            description.text = recItem.description
            priority.text = recItem.priority
            typeOfHabit.text = recItem.typeOfHabit
            periodicity.text = recItem.periodicity
            itemView.setBackgroundColor(recItem.color)
            color = recItem.color
            //change color of drawable resource
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.round_button)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, color)

            itemView.setBackgroundResource(R.drawable.round_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.pattern_of_item_of_rec_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val pos = holder.adapterPosition
        holder.bind(item, context)
        holder.itemView.setOnClickListener {
            clickListener.onRecyclerViewListClickListener(item, pos)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(habits: MutableList<RecItem>) {
        items = habits
        notifyDataSetChanged()
    }
}

