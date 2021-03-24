package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.recyclerview.Adapter
import com.swaptech.habitstwo.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.loadGoodHabitsToLocal
import kotlinx.android.synthetic.main.fragment_good_habits.*
import javax.inject.Inject

class GoodHabitsFragment : Fragment(), RecyclerViewClickListener {
    private val adapter by lazy {
        Adapter(mutableListOf(), requireContext(), this)
    }
    @Inject
    lateinit var viewModel: HabitsListViewModel
    /*
    var isNeedToUpdate: Boolean by Delegates.observable(false) {
        property, oldValue, newValue -> if(newValue) {
            if(this::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            }
        }
    }

     */

    companion object {
        fun newInstance() = GoodHabitsFragment()
    }

    override fun onRecyclerViewListClickListener(data: HabitForLocal, position: Int) {

        viewModel.position = position

        viewModel.goodHabits.value?.get(position)?.let {
            item = it
        }
        activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.nav_host_fragment,
                EditFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_good_habits, container, false)

        (requireActivity().application as App).applicationComponent.viewModelComponent().inject(this)
        /*
        val component = (requireActivity().application as App).applicationComponent
        viewModel = ViewModelProvider(this, object :  ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsListViewModel(
                    component.getAddHabitToLocalUseCase(),
                    component.getGetHabitsUseCase(),
                    component.getGetHabitsFromLocalUseCase(),
                    component.getDeleteAllFromLocalUseCase()) as T
            }
        } ).get(HabitsListViewModel::class.java)


         */

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()

        //Checking connection in ActivityCreated because NetworkReceiver registering in activity
        App.isConnected.observe(viewLifecycleOwner,  {
            if(it == true) {
                viewModel.getHabits()
            }
        })

        viewModel.goodHabits.observe(viewLifecycleOwner) {
            adapter.updateData(it ?: mutableListOf())
            viewModel.deleteAllFromLocal()
            viewModel.loadGoodHabitsToLocal()
        }
    }
    private fun setRecyclerView() {
        rec_view_good?.adapter = adapter
        rec_view_good.layoutManager = LinearLayoutManager(this.context)
    }

}
