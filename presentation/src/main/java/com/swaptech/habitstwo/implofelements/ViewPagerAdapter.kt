package com.swaptech.habitstwo.implofelements

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment

class ViewPagerAdapter(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager)  {
    private companion object {
        const val GOOD_HABITS = "Good"
        const val BAD_HABITS = "Bad"
    }


    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> HabitsListContainerFragment.newInstance(0)
        else -> HabitsListContainerFragment.newInstance(1)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> {
                App.res?.getString(R.string.good_type_of_habit_view_pager) ?: "Null"
            }
            else -> {
                App.res?.getString(R.string.bad_type_of_habit_view_pager) ?: "Null"
            }
        }
    }
}

 /*
class ViewPagerAdapter(private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val items = mutableListOf<Any>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_habits, parent, false))



    override fun getItemCount(): Int = items.size



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // bind your items
    }



    private inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



        init {
            itemView.setOnClickListener {

                itemClickListener(adapterPosition)
            }
        }
    }
}

 */