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
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.HabitUID
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment
import com.swaptech.habitstwo.mapper.DateConverter
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.android.synthetic.main.fragment_edit.*
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject

class EditFragment private constructor() : Fragment(){

    private var colorOfHabit = R.color.dark_grey

    private val calendar = Calendar.getInstance()

    private val colorPickerItems: MutableList<Int> by lazy {  mutableListOf(R.id.color_1_button,
            R.id.color_2_button, R.id.color_3_button, R.id.color_4_button, R.id.color_5_button,
            R.id.color_6_button, R.id.color_7_button, R.id.color_8_button, R.id.color_9_button,
            R.id.color_10_button, R.id.color_11_button, R.id.color_12_button, R.id.color_13_button,
            R.id.color_14_button, R.id.color_15_button, R.id.color_16_button)  }

    private var item: HabitForLocal = HabitForLocal(color = 0, count = 0, date = 0, description = "",
            doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    companion object {
        fun newInstance(editableFragment: HabitForLocal) = EditFragment().apply { item = editableFragment}
    }


    @Inject
    lateinit var viewModel: ActionsWithHabitFragmentViewModel

    //access to color from everywhere(in class)
    var color: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as App).applicationComponent
                .viewModelComponent().inject(this)
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val name: String = item.title ?: ""
        val description: String = item.description ?: ""
        val typeOfHabit: String = item.type ?: ""
        val periodicity: String = "${item.doneDates} ${item.frequency}" ?: ""
        val priority: String = item.priority ?: ""
        val countOfExecsOfHabit: Int = item.count
        val frequencyOfExecs: Int = item.frequency
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
                //val hex = '#' + Integer.toHexString(colorOfHabit).substring(2)
                //Toast.makeText(requireContext(), "$colorOfHabit - $hex", Toast.LENGTH_SHORT).show()
            }
        }

        name_entry.setText(item.title)
        description_entry.setText(item.description)
        period_of_exec_of_habit.setText(item.count.toString())
        num_of_execs_of_habit_entry.setText(item.frequency.toString())

        if (item.type == getString(R.string.good_radio_button)) {
            type_good_habit.isChecked = true
        } else {
            type_bad_habit.isChecked = true
        }

        when (item.priority) {
            getString(R.string.high_priority) -> priority_entry_activity.setSelection(0)
            getString(R.string.medium_priority) -> priority_entry_activity.setSelection(1)
            getString(R.string.low_priority) -> priority_entry_activity.setSelection(2)
        }

        checkCompleting()

        name_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                this.hide()
            }
            false
        }

        name_entry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                item.title = name_entry.text.toString()
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

                    item.type = type_good_habit.text.toString()
                    Toast.makeText(requireContext(), item.type, Toast.LENGTH_SHORT).show()
                }
                R.id.type_bad_habit -> {

                    item.type = type_bad_habit.text.toString()
                    Toast.makeText(requireContext(), item.type, Toast.LENGTH_SHORT).show()
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
                    item.count = viewModel.countOfExecsOfHabit
                    item.frequency = viewModel.frequencyOfExecs
                    viewModel.periodicity = "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
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
                viewModel.periodicity =
                        "${viewModel.countOfExecsOfHabit} ${getString(R.string.time_s_every)} ${viewModel.frequencyOfExecs} ${getString(R.string.days)}"
                checkCompleting()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        delete_button.setOnClickListener {
            if(App.isConnected.value == true) {
                viewModel.deleteFromServer(HabitUID(item.uid))
            } else {
                Toast.makeText(requireContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show()
            }
            openHabitsFragment()
        }

    }

    fun checkCompleting() {
        if (item.title.isNotEmpty() == true
                && item.description.isNotEmpty() == true
                && item.priority.isNotEmpty() == true
                && item.type.isNotEmpty() == true
                && viewModel.periodicity.isNotEmpty() == true
                && viewModel.countOfExecsOfHabit > 0
                && viewModel.frequencyOfExecs > 0) {
            button_complete_creating_habit.visibility = View.VISIBLE

            button_complete_creating_habit.setOnClickListener {
                val day = calendar.get(Calendar.DATE)
                val month = calendar.get(Calendar.MONTH)
                val date = DateConverter().convertDayAndMonth(day, month)
                item = HabitForLocal(title = item.title, description =  item.description,
                        priority = item.priority, type = item.type,
                        count =  viewModel.countOfExecsOfHabit,
                        frequency = viewModel.frequencyOfExecs,
                        color = colorOfHabit, doneDates = item.doneDates, date = date,
                        uid = item.uid)
                val habitToServer = HabitsAndHabitsForLocalConverter().serializeToHabit(item)

                viewModel.refreshHabitForServer(habitToServer)
                openHabitsFragment()

                clear()
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
            viewModel.priority = item.priority
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }
    private fun openHabitsFragment() {
        activity?.supportFragmentManager?.inTransaction {
            it.replace(R.id.nav_host_fragment, HabitsListContainerFragment.newInstance())
        }
    }
}