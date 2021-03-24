package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.recyclerview.Adapter
import com.swaptech.habitstwo.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.loadBadHabitsToLocal
import kotlinx.android.synthetic.main.fragment_bad_habits.*
import javax.inject.Inject

class BadHabitsFragment : Fragment(), RecyclerViewClickListener {
    private val adapter: Adapter by lazy {
        Adapter(mutableListOf(), requireContext(), this)
    }

    @Inject
    lateinit var viewModel: HabitsListViewModel

    companion object {
        fun newInstance() = BadHabitsFragment()
    }

    override fun onRecyclerViewListClickListener(data: HabitForLocal, position: Int) {
        viewModel.position = position

        viewModel.badHabits.value?.get(position)?.let {
            item = it
        }

        activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.nav_host_fragment,
                EditFragment())?.addToBackStack(null)
                ?.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bad_habits, container, false)
        (requireActivity().application as App).applicationComponent.viewModelComponent().inject(this)
        /*
        val component = (requireActivity().application as App).applicationComponent
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsListViewModel(
                    component.getAddHabitToLocalUseCase(),
                    component.getGetHabitsUseCase(),
                    component.getGetHabitsFromLocalUseCase(),
                    component.getDeleteAllFromLocalUseCase()) as T
            }
        }).get(HabitsListViewModel::class.java)


         */
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()

        App.isConnected.observe(viewLifecycleOwner,  {
            if(it == true) {
                viewModel.getHabits()
            }
        })
        viewModel.badHabits.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.updateData(it)

                viewModel.loadBadHabitsToLocal()
            }
        })

    }

    private fun setRecyclerView() {
        rec_view_bad?.adapter = adapter
        rec_view_bad?.layoutManager = LinearLayoutManager(this.context)
    }

}