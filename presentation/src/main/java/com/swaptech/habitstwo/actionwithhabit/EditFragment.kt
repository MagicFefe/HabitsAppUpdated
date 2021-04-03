package com.swaptech.habitstwo.actionwithhabit

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.HabitUID
import com.swaptech.habitstwo.*
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment
import com.swaptech.habitstwo.DateConverter
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import com.swaptech.habitstwo.navigation.MainActivity
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.button_complete_creating_habit
import kotlinx.android.synthetic.main.fragment_edit.colorViewer
import kotlinx.android.synthetic.main.fragment_edit.description_entry
import kotlinx.android.synthetic.main.fragment_edit.name_entry
import kotlinx.android.synthetic.main.fragment_edit.num_of_execs_of_habit_entry
import kotlinx.android.synthetic.main.fragment_edit.period_of_exec_of_habit
import kotlinx.android.synthetic.main.fragment_edit.textColorViewer
import kotlinx.android.synthetic.main.fragment_edit.type_bad_habit
import kotlinx.android.synthetic.main.fragment_edit.type_entry
import kotlinx.android.synthetic.main.fragment_edit.type_good_habit
import java.util.*
import javax.inject.Inject

class EditFragment: Fragment() {

    private val calendar = Calendar.getInstance()

    private val colorPickerItems: MutableList<Int> by lazy {  mutableListOf(R.id.color_1_button,
            R.id.color_2_button, R.id.color_3_button, R.id.color_4_button, R.id.color_5_button,
            R.id.color_6_button, R.id.color_7_button, R.id.color_8_button, R.id.color_9_button,
            R.id.color_10_button, R.id.color_11_button, R.id.color_12_button, R.id.color_13_button,
            R.id.color_14_button, R.id.color_15_button, R.id.color_16_button)  }

    private var item: HabitForLocal = HabitForLocal(color = 0, count = 0, date = 0, description = "",
            doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    private var nonEditableItem: HabitForLocal = HabitForLocal(color = 0, count = 0, date = 0, description = "",
            doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    companion object {
        fun newInstance(editableFragment: HabitForLocal): EditFragment {
            return EditFragment().apply {
                item = editableFragment
                nonEditableItem = editableFragment
            }
        }
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
        viewModel.getHabitsFromLocal()
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name: String = item.title
        val description: String = item.description
        val typeOfHabit: String = item.type
        val periodicity = "${item.count} ${item.frequency}"
        val priority: String = item.priority
        val countOfExecsOfHabit: Int = item.count
        val frequencyOfExecs: Int = item.frequency


        viewModel.name = name
        viewModel.description = description
        viewModel.typeOfHabit = typeOfHabit
        viewModel.periodicity = periodicity
        viewModel.priority = priority
        viewModel.countOfExecsOfHabit = countOfExecsOfHabit
        viewModel.frequencyOfExecs = frequencyOfExecs

        @SuppressLint("ResourceAsColor")
        for (item in colorPickerItems) {
            val button = activity?.findViewById<Button>(item)
            button?.setOnClickListener {

                val color = Color.parseColor(button.getButtonColorFromTag())

                val red = Color.red(color)
                val green = Color.green(color)
                val blue = Color.blue(color)

                this.item.color = color
                colorViewer.setBackgroundColor(color)

                textColorViewer.text = "R: $red G: $green B: $blue"

                //val hex = '#' + Integer.toHexString(colorOfHabit).substring(2)
                //Toast.makeText(requireContext(), "$colorOfHabit - $hex", Toast.LENGTH_SHORT).show()
            }
        }
        colorViewer.setBackgroundColor(item.color)
        val red = Color.red(item.color)
        val green = Color.green(item.color)
        val blue = Color.blue(item.color)
        textColorViewer.text = "R: $red G: $green B: $blue"
        name_entry.setText(item.title)
        description_entry.setText(item.description)
        period_of_exec_of_habit.setText(item.frequency.toString())
        num_of_execs_of_habit_entry.setText(item.count.toString())

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
                this.hideKeyboard()
            }
            false
        }

        name_entry.onTextChangedListener { text ->
            item.title = text.toString()
            checkCompleting()
        }

        description_entry.onTextChangedListener { text ->
            item.description = text.toString()
        }

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
        }

        num_of_execs_of_habit_entry.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hideKeyboard()
            }
            false
        }

        num_of_execs_of_habit_entry.onTextChangedListener { text ->
            item.count = try {
                text?.toString()?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }

            Toast.makeText(requireContext(), "${item.count}", Toast.LENGTH_SHORT).show()
            checkCompleting()
        }
        period_of_exec_of_habit.setOnKeyListener { _, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER)) {
                this.hideKeyboard()
            }
            checkCompleting()
            false
        }


        period_of_exec_of_habit.onTextChangedListener { text ->
            item.frequency = try {
                text?.toString()?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
            checkCompleting()
        }
        delete_button.setOnClickListener {
            if(App.isConnected.value == true) {
                viewModel.deleteFromServer(HabitUID(item.uid))
                val preferences = (requireActivity() as MainActivity).preferences.all
                val countKey = "${nonEditableItem.title} $APP_PREFERENCES_COUNT"
                val finalDateKey = "${nonEditableItem.title} $APP_PREFERENCES_FINAL_DATE"
                val countOfExecsKey = "${nonEditableItem.title} $APP_PREFERENCES_COUNT_OF_EXECS"
                if(preferences.containsKey(countKey)
                        && preferences.containsKey(finalDateKey)
                        && preferences.containsKey(countOfExecsKey)) {
                    val editor = (requireActivity() as MainActivity).preferences.edit()
                    editor.remove(countKey)
                    editor.remove(finalDateKey)
                    editor.remove(countOfExecsKey)
                    editor.apply()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_toast), Toast.LENGTH_SHORT).show()
            }
            activity?.supportFragmentManager?.inTransaction {
                it.replace(R.id.nav_host_fragment, HabitsListContainerFragment.newInstance())
            }
        }

    }

    private fun checkCompleting() {
        if (item.title.isNotEmpty()
                && item.description.isNotEmpty()
                && item.priority.isNotEmpty()
                && item.type.isNotEmpty()
                && item.count > 0
                && item.frequency > 0) {

            button_complete_creating_habit.visibility = View.VISIBLE

            button_complete_creating_habit.setOnClickListener {
                if(viewModel.checkUniquenessOfTitle(item.title) || viewModel.nonUniqueHabit.uid == item.uid) {
                    val dateConverter = DateConverter()
                    val day = calendar.get(Calendar.DATE)
                    val month = calendar.get(Calendar.MONTH)
                    val seconds = calendar.get(Calendar.SECOND)
                    val minutes = calendar.get(Calendar.MINUTE)
                    val hour = calendar.get(Calendar.HOUR)

                    var date = dateConverter.convertDayAndMonth(day, month+1)

                    date = (date.toString() + hour + minutes + seconds).toInt()

                    val endMonth = dateConverter.convertDaysToMonths(item.frequency)
                    val endDay = item.frequency - dateConverter.convertMonthsToDays(endMonth)
                    val endDate = dateConverter.generateEndDate(day, month, endDay, endMonth)

                    item.apply {
                        this.date = date
                    }

                    val editor = (requireActivity() as MainActivity).preferences.edit()

                    editor.remove("${nonEditableItem.title} $APP_PREFERENCES_COUNT")
                    editor.remove("${nonEditableItem.title} $APP_PREFERENCES_FINAL_DATE")

                    editor.putInt("${item.title} $APP_PREFERENCES_COUNT", item.count)
                    editor.putInt("${item.title} $APP_PREFERENCES_FINAL_DATE", endDate)

                    editor.apply()

                    val habitToServer = HabitsAndHabitsForLocalConverter().serializeToHabit(item)
                    if(App.isConnected.value == true) {
                        viewModel.refreshHabitForServer(habitToServer)
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.no_internet_toast), Toast.LENGTH_SHORT).show()
                    }
                    activity?.supportFragmentManager?.inTransaction {
                        it.replace(R.id.nav_host_fragment, HabitsListContainerFragment.newInstance())
                    }
                    clear()
                } else {
                    Toast.makeText(requireContext(), "Please, change title", Toast.LENGTH_SHORT).show()
                }
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
}