package com.swaptech.habitstwo.listhabits

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.HabitDone
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.implofelements.recyclerview.Adapter
import com.swaptech.habitstwo.implofelements.recyclerview.ButtonOfRecViewClickListener
import com.swaptech.habitstwo.implofelements.recyclerview.RecyclerViewClickListener
import com.swaptech.habitstwo.DateConverter
import com.swaptech.habitstwo.navigation.MainActivity
import kotlinx.android.synthetic.main.fragment_list_of_habits.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*


class ListOfHabitsFragment: Fragment(), RecyclerViewClickListener, ButtonOfRecViewClickListener {

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

    private val typeGood = App.res?.getString(R.string.good_radio_button)
    private val typeBad = App.res?.getString(R.string.bad_radio_button)
    private val calendar = Calendar.getInstance()

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

    @ExperimentalCoroutinesApi
    override fun onRecyclerViewListClickListener(habit: HabitForLocal, position: Int) {

        sharedViewModel.position = position

        activity?.supportFragmentManager?.inTransaction { transaction ->
            transaction.replace(
                R.id.nav_host_fragment,
                EditFragment.newInstance(habit)
            ).addToBackStack(null)
        }

    }

    @ExperimentalCoroutinesApi
    override fun onButtonOfRecViewClickListener(habit: HabitForLocal, position: Int) {
        var preferences: MutableMap<String, *> = (requireActivity() as MainActivity).preferences.all
        val countOfExecsKey = "${habit.title} $APP_PREFERENCES_COUNT_OF_EXECS"
        val countKey = "${habit.title} $APP_PREFERENCES_COUNT"
        val finalDateKey = "${habit.title} $APP_PREFERENCES_FINAL_DATE"
        val dateConverter = DateConverter()
        if (preferences.containsKey(countOfExecsKey)
                && preferences.containsKey(countKey)
                && preferences.containsKey(finalDateKey)) {

            val editor: SharedPreferences.Editor = (requireActivity() as MainActivity).preferences.edit()
            val value = preferences[countOfExecsKey] as Int
            editor.putInt(countOfExecsKey, value + 1)
            editor.apply()
            preferences = (requireActivity() as MainActivity).preferences.all
            val countOfExecs = preferences[countOfExecsKey] as Int
            val count = preferences[countKey] as Int
            val finalDate = preferences[finalDateKey] as Int
            val howManyNeedToDo = count - countOfExecs
            val currDay = calendar.get(Calendar.DATE)
            val currMonth = calendar.get(Calendar.MONTH) + 1
            val currDate = dateConverter.convertDayAndMonth(currDay, currMonth)
            //Toast.makeText(requireContext(), "final - $finalDate", Toast.LENGTH_SHORT).show()
            //Toast.makeText(requireContext(), "curr - $currDate", Toast.LENGTH_SHORT).show()
            val currMonthConverted = currDate.toString().substring(currDate.toString().lastIndex - 1).toInt()
            val finalMonthConverted = finalDate.toString().substring(finalDate.toString().lastIndex - 1).toInt()
            if (currMonthConverted <= finalMonthConverted) {
                if (habit.type == typeGood) {
                    if (howManyNeedToDo > 0) {
                        Toast.makeText(requireContext(), "Worth doing $howManyNeedToDo more times", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "You are breathtaking!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (howManyNeedToDo > 0) {
                        Toast.makeText(requireContext(), "Can be done $howManyNeedToDo more times", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Stop doing it!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if (App.isConnected.value == true) {
                    sharedViewModel.setHabitIsCompletedOnServer(HabitDone(currDate, habit.uid))
                }
                Toast.makeText(requireContext(), "Habit done", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)
        (requireActivity().application as App).applicationComponent.viewModelComponent().inject(this)
        return view
    }
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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