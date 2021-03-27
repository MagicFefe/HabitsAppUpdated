package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.implofelements.recyclerview.Adapter
import com.swaptech.habitstwo.implofelements.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.implofelements.recyclerview.ButtonOfRecViewClickListener
import com.swaptech.habitstwo.inTransaction
import kotlinx.android.synthetic.main.fragment_bad_habits.*
import javax.inject.Inject

class BadHabitsFragment : Fragment(), RecyclerViewClickListener, ButtonOfRecViewClickListener {

    private val adapter: Adapter by lazy {
        Adapter(mutableListOf(), requireContext(), this, this)
    }

    @Inject
    lateinit var viewModel: HabitsListViewModel

    companion object {
        fun newInstance() = BadHabitsFragment()
    }

    override fun onRecyclerViewListClickListener(habit: HabitForLocal, position: Int) {
        var clickedItem = habit
        viewModel.position = position

        viewModel.badHabits.value?.get(position)?.let {
            clickedItem = it
        }

        activity?.supportFragmentManager?.inTransaction { transaction ->
            transaction.replace(
                    R.id.nav_host_fragment,
                    EditFragment.newInstance(clickedItem)).addToBackStack(null)

        }
    }

    override fun onCheckBoxOfRecViewClickListener(position: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bad_habits, container, false)
        (requireActivity().application as App).applicationComponent
                .viewModelComponent().inject(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()

        viewModel.getHabitsFromLocal(HabitsListViewModel.searchFilter)

        App.isConnected.observe(viewLifecycleOwner,  {
            if(it == true) {
                viewModel.getHabits()
            }
        })



        viewModel.badHabits.observe(viewLifecycleOwner, { habits ->
            habits?.let { adapter.updateData(it) }
        })
    }

    private fun setRecyclerView() {
        rec_view_bad?.adapter = adapter
        rec_view_bad?.layoutManager = LinearLayoutManager(this.context)
    }

}