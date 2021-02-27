package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem
import com.swaptech.habitstwo.recyclerview.Adapter
import com.swaptech.habitstwo.recyclerview.RecyclerViewClickListener

class GoodHabitsFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var adapter: Adapter
    private lateinit var viewModel: HabitsListViewModel
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

    override fun onRecyclerViewListClickListener(data: RecItem, position: Int) {

        viewModel.position = position
        item = viewModel._goodHabits[position]

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

        viewModel = ViewModelProvider(this).get(HabitsListViewModel::class.java)
        adapter = Adapter(viewModel._goodHabits, requireContext(), this)
        val recView = view.findViewById<RecyclerView>(R.id.rec_view_good)
        recView?.layoutManager = LinearLayoutManager(view.context)
        /*
        val marginLayoutParams = LinearLayout.LayoutParams(recView?.layoutParams)//ViewGroup.MarginLayoutParams(recView?.layoutParams)
        marginLayoutParams.setMargins(3, 3,3,3)
        recView?.layoutParams = marginLayoutParams


         */

        /*
        val margins = (recView.layoutParams as ConstraintLayout.LayoutParams).apply {
            leftMargin = 0
            rightMargin = 0
            topMargin = 0
            bottomMargin = 0
        }
        recView.layoutParams = margins

         */
        recView?.adapter = adapter
        //recView?.addItemDecoration(DividerItemDecoration(view.context, VERTICAL))



        return view

    }
}