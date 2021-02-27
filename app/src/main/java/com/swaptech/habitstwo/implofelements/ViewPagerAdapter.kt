package com.swaptech.habitstwo.implofelements

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.listhabits.BadHabitsFragment
import com.swaptech.habitstwo.listhabits.GoodHabitsFragment
import com.swaptech.habitstwo.listhabits.HabitsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager)  {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        /*
        val arguments = Bundle()
        val habitsFragment = HabitsFragment.newInstance()

        when(position) {
            0 -> arguments.putString(HabitsFragment.TYPE_OF_HABIT, "Bad")
            else -> arguments.putString(HabitsFragment.TYPE_OF_HABIT, "Good")
        }

        return habitsFragment


         */
        return when(position) {
            0 -> GoodHabitsFragment.newInstance()
            else -> BadHabitsFragment.newInstance()
        }



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