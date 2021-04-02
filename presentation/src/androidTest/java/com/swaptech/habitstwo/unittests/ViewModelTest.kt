package com.swaptech.habitstwo.unittests

import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitUID
import com.swaptech.habitstwo.utils.MainCoroutineRule
import com.swaptech.habitstwo.actionwithhabit.ActionsWithHabitFragmentViewModel
import com.swaptech.habitstwo.utils.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.lang.NullPointerException

//import com.google.common.truth.Truth.assertThat
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ViewModelTest {
    /*
    val api: Api = mock()
    val dao: HabitsFromServerDao = mock()
    val repo = RepositoryImpl(api, dao)
    ^ ^ ^ ^
    | | | |
    NOT WORKING
     */
    private val habit = HabitForLocal(color = 0, count = 0, date = 0, description = "",
            doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    private val habitToServer = Habit(color = 0, count = 0, date = 0, description = "", frequency = 0,
            priority = 0, title = "", type = 0, uid = "")

    private val habitUID = HabitUID(uid = "")

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private var viewModel: ActionsWithHabitFragmentViewModel = mock()

    @Test(expected = NullPointerException::class)
    fun `if method invoked then throw null pointer exception`() = coroutineRule.runBlockingTest {

        whenever(viewModel.addToServer(habitToServer)).thenReturn(Unit)
        whenever(viewModel.deleteFromServer(habitUID)).thenReturn(Unit)
        whenever(viewModel.refreshHabitForServer(habitToServer)).thenReturn(Unit)
        whenever(viewModel.addToLocal(habit)).thenReturn(Unit)
        whenever(viewModel.getHabitsFromLocal()).thenReturn(Unit)
    }

    @Test(expected = NullPointerException::class)
     fun `test for methods without coroutine`() {
         val result = viewModel.checkUniquenessOfTitle("")
         assertEquals(result, true)
     }
}