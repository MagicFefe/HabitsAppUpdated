package com.swaptech.habitstwo.actionwithhabit

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.App.Companion.item
import com.swaptech.habitstwo.listhabits.HabitsFragment
import com.swaptech.habitstwo.model.RecItem
import kotlinx.android.synthetic.main.fragment_edit.*
import java.lang.NumberFormatException

class EditFragment : Fragment()/*, View.OnClickListener, BottomNavViewSwitcher */ {
    private var colorOfHabit = R.color.dark_grey
    private lateinit var viewModel: ActionsWithHabitFragmentViewModel
    private val colorPickerItems: MutableList<Int> by lazy {  mutableListOf(R.id.color_1_button,
            R.id.color_2_button, R.id.color_3_button, R.id.color_4_button, R.id.color_5_button,
            R.id.color_6_button, R.id.color_7_button, R.id.color_8_button, R.id.color_9_button,
            R.id.color_10_button, R.id.color_11_button, R.id.color_12_button, R.id.color_13_button,
            R.id.color_14_button, R.id.color_15_button, R.id.color_16_button)  }
    //access to color from everywhere(in class)
    var color: Int = 0

    companion object {
        fun newInstance() = EditFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ActionsWithHabitFragmentViewModel::class.java)

        return inflater.inflate(R.layout.fragment_edit, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val name: String = item.name ?: ""
        val description: String = item.description ?: ""
        val typeOfHabit: String = item.typeOfHabit ?: ""
        val periodicity: String = item.periodicity ?: ""
        val priority: String = item.priority ?: ""
        val countOfExecsOfHabit: Int = item.countOfExecsOfHabit
        val frequencyOfExecs: Int = item.frequencyOfExecs
        val id: Int = item.id
        color = item.color
        colorOfHabit = color
        viewModel.name = name
        viewModel.description = description
        viewModel.typeOfHabit = typeOfHabit
        viewModel.periodicity = periodicity
        viewModel.priority = priority
        viewModel.countOfExecsOfHabit = countOfExecsOfHabit
        viewModel.frequencyOfExecs = frequencyOfExecs

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

        /*
        color_1_button.setOnClickListener(this)
        color_2_button.setOnClickListener(this)
        color_3_button.setOnClickListener(this)
        color_4_button.setOnClickListener(this)
        color_5_button.setOnClickListener(this)
        color_6_button.setOnClickListener(this)
        color_7_button.setOnClickListener(this)
        color_8_button.setOnClickListener(this)
        color_9_button.setOnClickListener(this)
        color_10_button.setOnClickListener(this)
        color_11_button.setOnClickListener(this)
        color_12_button.setOnClickListener(this)
        color_13_button.setOnClickListener(this)
        color_14_button.setOnClickListener(this)
        color_15_button.setOnClickListener(this)
        color_16_button.setOnClickListener(this)

         */

        //[START] restore data from recycler item
        checkCompleting()
        name_entry.setText(item.name)
        description_entry.setText(item.description)
        period_of_exec_of_habit.setText(item.countOfExecsOfHabit.toString())
        num_of_execs_of_habit_entry.setText(item.frequencyOfExecs.toString())
        if (item.typeOfHabit == getString(R.string.good_radio_button)) {
            type_good_habit.isChecked = true
        } else {
            type_bad_habit.isChecked = true
        }
        when (item.priority) {
            getString(R.string.high_priority) -> priority_entry_activity.setSelection(0)
            getString(R.string.medium_priority) -> priority_entry_activity.setSelection(1)
            getString(R.string.low_priority) -> priority_entry_activity.setSelection(2)
        }
        //[END] restore data from recycler item

        name_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
                this.hide()
            }
            false
        }

        name_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                item.name = name_entry.text.toString()
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        save_description.setOnClickListener {
            save_description.visibility = View.GONE
            this.hide()
            Toast.makeText(requireContext(), item.description, Toast.LENGTH_SHORT).show()
        }

        description_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                item.description = description_entry.text.toString()
                save_description.visibility = View.VISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        priority_entry_activity.onItemSelectedListener = SpinnerClickListenerImpl()

        type_entry.setOnCheckedChangeListener { _, i ->
            item.priority = viewModel.priority
            when (i) {
                R.id.type_good_habit -> {

                    item.typeOfHabit = type_good_habit.text.toString()
                    Toast.makeText(requireContext(), item.typeOfHabit, Toast.LENGTH_SHORT).show()
                }
                R.id.type_bad_habit -> {

                    item.typeOfHabit = type_bad_habit.text.toString()
                    Toast.makeText(requireContext(), item.typeOfHabit, Toast.LENGTH_SHORT).show()
                }
            }
            checkCompleting()
            Toast.makeText(requireContext(), item.priority, Toast.LENGTH_SHORT).show()
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
                if (viewModel.frequencyOfExecs > 0) {
                    item.periodicity =
                            "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
                }
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        period_of_exec_of_habit.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                Toast.makeText(requireContext(), item.priority, Toast.LENGTH_SHORT).show()
                this.hide()
            }
            checkCompleting()
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
                item.periodicity =
                        "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        delete_button.setOnClickListener {
            val habit = RecItem(name, description, priority, typeOfHabit, periodicity, countOfExecsOfHabit, frequencyOfExecs, color, id)
            viewModel.delete(habit)
            activity?.supportFragmentManager?.popBackStack()
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, HabitsFragment.newInstance())?.commit()
            //switchFragment(R.id.navigation_habits, R.id.bottom_navigation_drawer)

        }
    }

    fun checkCompleting() {
        if (item.name?.isNotEmpty() == true
                && item.description?.isNotEmpty() == true
                && item.priority?.isNotEmpty() == true
                && item.typeOfHabit?.isNotEmpty() == true
                && item.periodicity?.isNotEmpty() == true
                && viewModel.countOfExecsOfHabit > 0
                && viewModel.frequencyOfExecs > 0) {
            button_complete_creating_habit.visibility = View.VISIBLE
            button_complete_creating_habit.setOnClickListener {
                item = RecItem(item.name, item.description, item.priority, item.typeOfHabit,
                        item.periodicity, viewModel.countOfExecsOfHabit, viewModel.frequencyOfExecs,
                        colorOfHabit, item.id)
                viewModel.refreshHabit(item)
                activity?.supportFragmentManager?.popBackStack()
                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, HabitsFragment.newInstance())?.commit()
                //switchFragment(R.id.navigation_habits, R.id.bottom_navigation_drawer)
                clear()
            }
        }
    }

    private fun Fragment.hide() {
        this.activity?.currentFocus.let {
            val g = view?.let { it1 -> ContextCompat.getSystemService(it1.context, InputMethodManager::class.java) }
            g?.hideSoftInputFromWindow(it?.windowToken, 0)
        }
    }
    /*
    override fun switchFragment(viewId: Int, bottomNavViewId: Int) {
        val bottomBar = activity?.findViewById<BottomNavigationView>(bottomNavViewId)
        val view = bottomBar?.findViewById<View>(viewId)
        view?.performClick()
    }

     */
    /*
    @SuppressLint("ResourceAsColor")
    override fun onClick(view: View?) {
        view?.let {
            /*
            when(view.id) {
                R.id.color_1_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.green_color_picker)
                R.id.color_2_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.red_color_picker)
                R.id.color_3_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.cyan_color_picker)
                R.id.color_4_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.purple_500)
                R.id.color_5_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.purple)
                R.id.color_6_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_blue_color_picker)
                R.id.color_7_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_cyan_color_picker)
                R.id.color_8_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_light_yellow_color_picker)
                R.id.color_9_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_light_brown_color_picker)
                R.id.color_10_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_light_green_color_picker)
                R.id.color_11_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_pink_color_picker)
                R.id.color_12_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_orange_color_picker)
                R.id.color_13_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_purple_color_picker)
                R.id.color_14_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.light_yellow_color_picker)
                R.id.color_15_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.teal_200)
                R.id.color_16_button -> colorOfHabit = ContextCompat.getColor(requireContext(), R.color.teal_700)
            }
            color = colorOfHabit
            //val hex = '#' + Integer.toHexString(colorOfHabit).substring(2)
            //Toast.makeText(requireContext(), "$colorOfHabit - $hex", Toast.LENGTH_SHORT).show()

             */
            val itemId = view.id
            if (colorPickerItems.contains(itemId)) {
                val buttonColorPicker = activity?.findViewById<Button>(itemId)
                buttonColorPicker?.let {
                    colorOfHabit = Color.parseColor(it.getButtonColor())
                }
            }
        }
    }

     */

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