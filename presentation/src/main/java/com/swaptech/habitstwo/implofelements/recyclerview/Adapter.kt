package com.swaptech.habitstwo.implofelements.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.R



class Adapter(
        var items: MutableList<HabitForLocal>,
        val context: Context,
        private val clickListener: RecyclerViewClickListener,
        private val buttonClickListener: ButtonOfRecViewClickListener
)
    : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var color = 0

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.title_habits_item)
        var description = itemView.findViewById<TextView>(R.id.description_habits_item)
        var priority = itemView.findViewById<TextView>(R.id.priority_habits_item)
        var typeOfHabit = itemView.findViewById<TextView>(R.id.type_habits_item)
        var periodicity = itemView.findViewById<TextView>(R.id.periodicity_habits_item)
        var buttonOfRecView = itemView.findViewById<Button>(R.id.check_box_completing_habit)

        @SuppressLint("SetTextI18n")
        fun bind(habitForLocal: HabitForLocal, context: Context) {

            name.text = habitForLocal.title
            description.text = habitForLocal.description
            priority.text = habitForLocal.priority
            typeOfHabit.text = habitForLocal.type
            periodicity.text = "${habitForLocal.count} ${context.getString(R.string.time_s_every)} ${habitForLocal.frequency} ${context.getString(R.string.days)}"
            itemView.setBackgroundColor(habitForLocal.color)

            //change color of drawable resource
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.round_button)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            color = habitForLocal.color
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
        holder.buttonOfRecView.setOnClickListener {
            buttonClickListener.onCheckBoxOfRecViewClickListener(pos)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(habits: MutableList<HabitForLocal>) {
        items = habits
        notifyDataSetChanged()
    }

}

