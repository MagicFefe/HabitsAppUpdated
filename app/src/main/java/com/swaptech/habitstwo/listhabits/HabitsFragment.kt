package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.implofelements.ViewPagerAdapter
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_habits.*
import android.view.*

class HabitsFragment: Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private lateinit var viewModel: HabitsListViewModel


    companion object {
        fun newInstance(): HabitsFragment {
            return HabitsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HabitsListViewModel::class.java)

        return inflater.inflate(R.layout.fragment_habits, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view_pager.adapter = ViewPagerAdapter(childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)

        //Toast.makeText(requireContext(), "$typeOfHabit", Toast.LENGTH_SHORT).show()

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    //add_habit_button.animate().scaleX(1F).scaleY(1F).setDuration(200).start()
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
            HabitsListViewModel.searchFilter = null
            openFragment()
        }
        type_filter.setOnClickListener {
            Toast.makeText(this.activity, getString(R.string.help_with_filter_toast), Toast.LENGTH_LONG).show()
        }
        type_filter.setOnKeyListener { view, i, keyEvent ->

            if(keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {

                if(type_filter.text.toString().isNotEmpty()) {
                    HabitsListViewModel.searchFilter = type_filter.text.toString().toLowerCase()
                    hide()
                    openFragment()
                } else {
                    HabitsListViewModel.searchFilter = null
                }


            }
            //If returns true, then editText lock back button by clicking, because we need to return false
            false

        }

        add_habit_button.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, AddFragment.newInstance())?.addToBackStack(null)?.commit()

        }
    }

    private fun Fragment.hide() {
        this.activity?.currentFocus.let{
            val view = view?.let { it1 -> ContextCompat.getSystemService(it1.context, InputMethodManager::class.java) }
            view?.hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }
    private fun openFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, HabitsFragment.newInstance())?.commit()
    }
}


