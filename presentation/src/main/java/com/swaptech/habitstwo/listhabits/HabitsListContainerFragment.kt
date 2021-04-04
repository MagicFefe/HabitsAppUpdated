package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_habits.*
import android.view.*
import android.widget.Toast
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.implofelements.ViewPagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HabitsListContainerFragment: Fragment(), FragmentWithViewModel<HabitsListViewModel> {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    @Inject
    override lateinit var viewModel: HabitsListViewModel

    companion object {
        fun newInstance(): HabitsListContainerFragment {
            return HabitsListContainerFragment()
        }

    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity().application as App).applicationComponent
                .viewModelComponent().inject(this)

        return inflater.inflate(R.layout.fragment_habits, container, false)
    }


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if(App.isConnected.value == true) {
            viewModel.getHabits()
        }

        view_pager?.adapter = ViewPagerAdapter(childFragmentManager, this)
        tab_layout?.setupWithViewPager(view_pager)

        //Checking connection in ViewCreated because NetworkReceiver registering in activity

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    add_habit_button.animate().scaleX(0F).scaleY(0F).setDuration(200).start()

                } else if (BottomSheetBehavior.STATE_EXPANDED == newState && BottomSheetBehavior.STATE_COLLAPSED != newState) {

                    add_habit_button.animate().scaleX(0F).scaleY(0F).setDuration(200).start()
                    add_habit_button.visibility = View.GONE

                } else if (BottomSheetBehavior.STATE_EXPANDED != newState && BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    add_habit_button.visibility = View.VISIBLE
                    add_habit_button.animate().scaleX(1F).scaleY(1F).setDuration(200).start()

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        type_filter.onTextChangedListener {
            var searchFilter = type_filter.text.toString().toLowerCase()
            if(searchFilter.length > 1) {
                searchFilter = searchFilter[0].toUpperCase() + searchFilter.substring(1)
            }
            HabitsListViewModel.searchFilter.value = searchFilter
        }

        type_filter.setOnKeyListener { _, i, keyEvent ->

            if(keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                hideKeyboard()
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

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.sync_habits_button -> {
            if(App.isConnected.value == true) {
                viewModel.syncHabits()
            } else {
                Toast.makeText(requireContext(),
                        getString(R.string.no_internet_for_syncing_habits_toast),
                        Toast.LENGTH_SHORT).show()
            }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}


