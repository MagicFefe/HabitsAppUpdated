package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_habits.*
import android.view.*
import android.widget.Toast
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.hide
import com.swaptech.habitstwo.inTransaction
import javax.inject.Inject

class HabitsListContainerFragment: Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    @Inject
    lateinit var viewModel: HabitsListViewModel
    private var page = -1
    companion object {
        private const val GOOD_HABITS = 0
        private const val BAD_HABITS = 1
        fun newInstance(): HabitsListContainerFragment {
            return HabitsListContainerFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity().application as App).applicationComponent
                .viewModelComponent().inject(this)


        return inflater.inflate(R.layout.fragment_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            when(page) {
                //GOOD_HABITS -> tv_text?.text = "1"
                //BAD_HABITS -> tv_text?.text = "2"
            }

        Toast.makeText(requireContext(), "$page", Toast.LENGTH_SHORT).show()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //view_pager?.adapter = ViewPagerAdapter(childFragmentManager)
        //tab_layout?.setupWithViewPager(view_pager)
        /*
        activity?.let { activity ->
            view_pager.adapter = ViewPagerAdapter(activity as AppCompatActivity)
            /*
            TabLayoutMediator(tab_layout, view_pager) { tab, position ->

                when(position) {
                    0 -> {

                        tab.text = App.res?.getString(R.string.good_type_of_habit_view_pager)
                    }
                    else -> {
                        tab.text = App.res?.getString(R.string.bad_type_of_habit_view_pager)
                    }
                }
            }

             */
        }//?.attach()

         */



        /*
        App.isConnected.observe(viewLifecycleOwner, {
            if (it == true) {

                viewModel.getHabits()
            }
        })

         */

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    reset_button.animate().scaleX(0F).scaleY(0F).setDuration(200).start()

                } else if (BottomSheetBehavior.STATE_EXPANDED == newState && BottomSheetBehavior.STATE_COLLAPSED != newState) {

                    add_habit_button.animate().scaleX(0F).scaleY(0F).setDuration(200).start()
                    add_habit_button.visibility = View.GONE

                    reset_button.visibility = View.VISIBLE
                    reset_button.animate().scaleX(1F).scaleY(1F).setDuration(200).start()

                } else if (BottomSheetBehavior.STATE_EXPANDED != newState && BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    add_habit_button.visibility = View.VISIBLE
                    add_habit_button.animate().scaleX(1F).scaleY(1F).setDuration(200).start()

                    reset_button.animate().scaleX(0F).scaleY(0F).setDuration(200).start()
                    reset_button.visibility = View.GONE
                    type_filter.text.clear()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        reset_button.setOnClickListener {
            type_filter.text.clear()
            HabitsListViewModel.searchFilter = ""
            openFragment()
        }

        type_filter.setOnKeyListener { view, i, keyEvent ->

            if(keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                val input = type_filter.text.toString()
                if(input.isNotEmpty() && input.length > 1) {
                    var searchFilter = type_filter.text.toString().toLowerCase()
                    searchFilter = searchFilter[0].toUpperCase() + searchFilter.substring(1)
                    HabitsListViewModel.searchFilter = searchFilter
                } else {
                    HabitsListViewModel.searchFilter = ""
                }
                hide()
                openFragment()
            }

            //If returns true, then editText lock back button by clicking, because we need to return false
            false
        }

        add_habit_button.setOnClickListener {
            activity?.supportFragmentManager?.inTransaction { transaction ->
                transaction.replace(R.id.nav_host_fragment, AddFragment.newInstance())
                        .addToBackStack(null)
            }
        }
    }

    private fun openFragment() {
        activity?.supportFragmentManager?.inTransaction { transaction ->
            transaction.replace(R.id.nav_host_fragment, HabitsListContainerFragment.newInstance())
        }
    }
}


