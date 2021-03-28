package com.swaptech.habitstwo.implofelements

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.listhabits.FragmentWithViewModel
import com.swaptech.habitstwo.listhabits.HabitsListViewModel
import com.swaptech.habitstwo.listhabits.ListOfHabitsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,private val fragmentWithViewModel: FragmentWithViewModel<HabitsListViewModel>)
    : FragmentStatePagerAdapter(fragmentManager)  {
    private companion object {
        const val GOOD_HABITS = "Good"
        const val BAD_HABITS = "Bad"
    }


    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = ListOfHabitsFragment.newInstance(position, fragmentWithViewModel.viewModel)

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
