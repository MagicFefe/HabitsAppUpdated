package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.implofelements.recyclerview.Adapter
import com.swaptech.habitstwo.implofelements.recyclerview.ButtonOfRecViewClickListener
import com.swaptech.habitstwo.implofelements.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.inTransaction
import kotlinx.android.synthetic.main.fragment_list_of_habits.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


class ListOfHabitsFragment private constructor(): Fragment(), RecyclerViewClickListener, ButtonOfRecViewClickListener {

    private val adapter by lazy {
        Adapter(mutableListOf(), requireContext(), this, this)
    }

    lateinit var sharedViewModel: HabitsListViewModel

    /*
    var isNeedToUpdate: Boolean by Delegates.observable(false) {
        property, oldValue, newValue -> if(newValue) {
            if(this::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
        }
    }

     */
    private var page = -1

    companion object {
        private const val GOOD_HABITS = 0
        private const val BAD_HABITS = 1

        fun newInstance(numOfPage: Int, viewModel: HabitsListViewModel): ListOfHabitsFragment {
            return ListOfHabitsFragment().apply {
                page = numOfPage
                sharedViewModel = viewModel
            }
        }
    }

    override fun onRecyclerViewListClickListener(habit: HabitForLocal, position: Int) {

        sharedViewModel.position = position

        activity?.supportFragmentManager?.inTransaction { transaction ->
            transaction.replace(
                R.id.nav_host_fragment,
                EditFragment.newInstance(habit)
            ).addToBackStack(null)
        }

    }

    override fun onCheckBoxOfRecViewClickListener(position: Int) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)

        return view
    }
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val typeGood = App.res?.getString(R.string.good_radio_button)
        val typeBad = App.res?.getString(R.string.bad_radio_button)

        setRecyclerView()

        sharedViewModel.habits.observe(viewLifecycleOwner, { habits ->
            when (page) {
                GOOD_HABITS -> {
                    adapter.updateData(habits.filter { it.type == typeGood } as MutableList<HabitForLocal>)
                }
                BAD_HABITS -> {
                    adapter.updateData(habits.filter { it.type == typeBad } as MutableList<HabitForLocal>)
                }

            }
        })
    }

    private fun setRecyclerView() {
        rec_view_good?.adapter = adapter
        rec_view_good.layoutManager = LinearLayoutManager(this.context)
    }
}