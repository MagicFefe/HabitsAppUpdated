package com.swaptech.habitstwo.actionwithhabit

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment
import com.swaptech.habitstwo.mapper.DateConverter
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*
import javax.inject.Inject

class AddFragment private constructor(): Fragment() {

    private var colorOfHabit = R.color.dark_grey
    private val calendar = Calendar.getInstance()
    private val colorPickerItems: MutableList<Int> by lazy { mutableListOf(R.id.color_1_button,
            R.id.color_2_button, R.id.color_3_button, R.id.color_4_button, R.id.color_5_button,
            R.id.color_6_button, R.id.color_7_button, R.id.color_8_button, R.id.color_9_button,
            R.id.color_10_button, R.id.color_11_button, R.id.color_12_button, R.id.color_13_button,
            R.id.color_14_button, R.id.color_15_button, R.id.color_16_button)
    }

    @Inject
    lateinit var viewModel: ActionsWithHabitFragmentViewModel

    companion object {
        fun newInstance() = AddFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        (requireActivity().application as App).applicationComponent
                .viewModelComponent().inject(this)
        return inflater.inflate(R.layout.fragment_add, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        @SuppressLint("ResourceAsColor")
        for (item in colorPickerItems) {
            activity?.findViewById<Button>(item)?.setOnClickListener {
                val viewId = it.id
                val button = activity?.findViewById<Button>(viewId)
                button?.let {
                    colorOfHabit = Color.parseColor(button.getButtonColor())

                }
                //val hex = '#' + Integer.toHexString(colorOfHabit).substring(2)
                //Toast.makeText(requireContext(), "$colorOfHabit - $hex", Toast.LENGTH_SHORT).show()
            }
        }

        name_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {

                hideKeyboard()
            }
            false
        }


        name_entry.onTextChangedListener {
            viewModel.name = name_entry.text.toString()
            checkCompleting()
        }

        save_description.setOnClickListener {

            hideKeyboard()
            save_description.visibility = View.GONE
        }

        description_entry.onTextChangedListener {
            viewModel.description = description_entry.text.toString()
            save_description.visibility = View.VISIBLE
            checkCompleting()
        }

        priority_entry_fragment.onItemSelectedListener = SpinnerClickListenerImpl()

        type_entry.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.type_good_habit -> {
                    viewModel.typeOfHabit = type_good_habit.text.toString()
                }
                R.id.type_bad_habit -> {
                    viewModel.typeOfHabit = type_bad_habit.text.toString()
                }
            }
            checkCompleting()

        }

        num_of_execs_of_habit_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hideKeyboard()
            }
            false
        }

        num_of_execs_of_habit_entry.onTextChangedListener {
            viewModel.countOfExecsOfHabit = try {
                num_of_execs_of_habit_entry?.text?.toString()?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
            if (viewModel.frequencyOfExecs <= 0) {
                viewModel.periodicity = "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} "
            } else {
                viewModel.periodicity =
                        "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
            }
            checkCompleting()
        }

        period_of_exec_of_habit.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hideKeyboard()
            }
            false
        }

        period_of_exec_of_habit.onTextChangedListener {
            viewModel.frequencyOfExecs = try {
                period_of_exec_of_habit?.text?.toString()?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
            viewModel.periodicity =
                    "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
            checkCompleting()
        }

        button_complete_creating_habit.setOnClickListener {

            val result = checkCompleting()
            if (result) {
                val day = calendar.get(Calendar.DATE)

                //Increment value, because in Calendar class num of month starts from zero
                val month = calendar.get(Calendar.MONTH) + 1
                val date = DateConverter().convertDayAndMonth(day, month)
                val habitForLocal = HabitForLocal(color = colorOfHabit,
                        count = viewModel.countOfExecsOfHabit, date = date,
                        description = viewModel.description, frequency = viewModel.frequencyOfExecs,
                        priority = viewModel.priority, title = viewModel.name,
                        type = viewModel.typeOfHabit, uid = "")

                addHabit(habitForLocal)

            } else {
                Toast.makeText(requireContext(), getString(R.string.error_adding_habit_toast), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkCompleting(): Boolean {
        if (viewModel.name.isNotEmpty()
                && viewModel.description.isNotEmpty()
                && viewModel.priority.isNotEmpty()
                && viewModel.typeOfHabit.isNotEmpty()
                && viewModel.periodicity.isNotEmpty()
                && viewModel.countOfExecsOfHabit > 0
                && viewModel.frequencyOfExecs > 0) {
            return true
        }
        return false
    }

    private fun clear() {
        viewModel.name = ""
        viewModel.countOfExecsOfHabit = 0
        viewModel.description = ""
        viewModel.frequencyOfExecs = 0
        viewModel.periodicity = ""
        viewModel.priority = ""
        viewModel.typeOfHabit = ""
    }

    inner class SpinnerClickListenerImpl : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.priority = parent?.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }

    private fun addHabit(habitForLocal: HabitForLocal) {

        val converter = HabitsAndHabitsForLocalConverter()
        val habit = converter.serializeToHabit(habitForLocal)
        viewModel.addToLocal(habitForLocal)

        if (App.isConnected.value == true) {
            viewModel.addToServer(habit)
        }
        clear()
        exitFromFragment()
    }

    private fun exitFromFragment() {
        activity?.supportFragmentManager?.popBackStack()
        activity?.supportFragmentManager?.inTransaction { transaction ->
            transaction.add(R.id.nav_host_fragment, HabitsListContainerFragment.newInstance())
        }
        hideKeyboard()
    }
}