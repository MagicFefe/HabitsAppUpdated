package com.swaptech.habitstwo.listhabits

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swaptech.habitstwo.recyclerview.Adapter
import com.swaptech.habitstwo.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem

class BadHabitsFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var adapter: Adapter
    private lateinit var viewModel: HabitsListViewModel
    companion object {
        fun newInstance() = BadHabitsFragment()
    }

    override fun onRecyclerViewListClickListener(data: RecItem, position: Int) {
        viewModel.position = position
        item = viewModel._badHabits[position]

        activity?.supportFragmentManager?.beginTransaction()?.replace(
            R.id.nav_host_fragment,
            EditFragment())?.addToBackStack(null)
            ?.commit()




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bad_habits, container, false)

        viewModel = ViewModelProvider(this).get(HabitsListViewModel::class.java)
        adapter = Adapter(viewModel._badHabits, requireContext(), this)

        val recView = view.findViewById<RecyclerView>(R.id.rec_view_bad)
        recView?.layoutManager = LinearLayoutManager(view.context)
        recView?.adapter = adapter
        //recView?.addItemDecoration(DividerItemDecoration(view.context, VERTICAL))
        adapter.notifyDataSetChanged()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        this.view?.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    return true
                }
                return false
            }
        })

        return view
    }


}