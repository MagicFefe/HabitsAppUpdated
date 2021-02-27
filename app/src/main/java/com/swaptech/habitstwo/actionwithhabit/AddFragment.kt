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
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.listhabits.HabitsFragment
import com.swaptech.habitstwo.model.RecItem
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment: Fragment() {

    private var colorOfHabit = R.color.dark_grey
    private lateinit var viewModel: ActionsWithHabitFragmentViewModel
    private val colorPickerItems: MutableList<Int> by lazy {  mutableListOf(R.id.color_1_button,
            R.id.color_2_button, R.id.color_3_button, R.id.color_4_button, R.id.color_5_button,
            R.id.color_6_button, R.id.color_7_button, R.id.color_8_button, R.id.color_9_button,
            R.id.color_10_button, R.id.color_11_button, R.id.color_12_button, R.id.color_13_button,
            R.id.color_14_button, R.id.color_15_button, R.id.color_16_button)  }

    companion object {
        fun newInstance() = AddFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ActionsWithHabitFragmentViewModel::class.java)
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
                val hex = '#' + Integer.toHexString(colorOfHabit).substring(2)
                Toast.makeText(requireContext(), "$colorOfHabit - $hex", Toast.LENGTH_SHORT).show()
            }
        }

        name_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {

                Toast.makeText(this.activity, viewModel.name, Toast.LENGTH_SHORT).show()
                this.hide()
            }
            false
        }

        name_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.name = name_entry.text.toString()
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        save_description.setOnClickListener {
            Toast.makeText(this.activity, viewModel.description, Toast.LENGTH_SHORT).show()
            this.hide()
            save_description.visibility = View.GONE
        }

        description_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.description = description_entry.text.toString()
                save_description.visibility = View.VISIBLE
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        priority_entry_fragment.onItemSelectedListener = SpinnerClickListenerImpl()

        type_entry.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.type_good_habit -> {
                    viewModel.typeOfHabit = type_good_habit.text.toString()
                    Toast.makeText(this.activity, viewModel.typeOfHabit, Toast.LENGTH_SHORT).show()

                }
                R.id.type_bad_habit -> {
                    viewModel.typeOfHabit = type_bad_habit.text.toString()
                    Toast.makeText(this.activity, viewModel.typeOfHabit, Toast.LENGTH_SHORT).show()
                }
            }
            checkCompleting()
            Toast.makeText(this.activity, viewModel.priority, Toast.LENGTH_SHORT).show()
        }

        num_of_execs_of_habit_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hide()
            }
            false
        }

        num_of_execs_of_habit_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

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

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        period_of_exec_of_habit.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hide()
            }

            false
        }

        period_of_exec_of_habit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.frequencyOfExecs = try {
                    period_of_exec_of_habit?.text?.toString()?.toInt() ?: 0
                } catch (e: NumberFormatException) {
                    0
                }
                viewModel.periodicity =
                        "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }


    private fun Fragment.hide() {
        this.activity?.currentFocus.let {
            val g = view?.let { it1 -> ContextCompat.getSystemService(it1.context, InputMethodManager::class.java) }
            g?.hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }

    fun checkCompleting() {
        if (viewModel.name.isNotEmpty()
                && viewModel.description.isNotEmpty()
                && viewModel.priority.isNotEmpty()
                && viewModel.typeOfHabit.isNotEmpty()
                && viewModel.periodicity.isNotEmpty()
                && viewModel.countOfExecsOfHabit > 0
                && viewModel.frequencyOfExecs > 0) {
            button_complete_creating_habit.visibility = View.VISIBLE
            button_complete_creating_habit.setOnClickListener {
                viewModel.habit = RecItem(
                        viewModel.name, viewModel.description, viewModel.priority,
                        viewModel.typeOfHabit, viewModel.periodicity,
                        viewModel.countOfExecsOfHabit, viewModel.frequencyOfExecs, colorOfHabit, 0
                )
                if (viewModel.typeOfHabit == getString(R.string.good_radio_button)) {
                    viewModel._goodHabits.add(viewModel.habit)
                } else {
                    viewModel._badHabits.add(viewModel.habit)
                }

                viewModel.add()
                Toast.makeText(this.activity, getString(R.string.successful_added_habit_toast), Toast.LENGTH_LONG).show()
                clear()
                //findNavController().navigate(R.id.action_addFragment_to_habitsFragment)
                activity?.supportFragmentManager?.popBackStack()
                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, HabitsFragment.newInstance())?.commit()
            }
        }
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
    inner class SpinnerClickListenerImpl: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            item.priority = parent?.getItemAtPosition(position).toString()
            viewModel.priority = item.priority ?: ""
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }

}