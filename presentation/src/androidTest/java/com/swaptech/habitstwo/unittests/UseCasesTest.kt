package com.swaptech.habitstwo.unittests

import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitDone
import com.swaptech.domain.models.HabitUID
import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.utils.MainCoroutineRule
import com.swaptech.habitstwo.utils.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.lang.NullPointerException

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UseCasesTest {

    private val habit = HabitForLocal(color = 0, count = 0, date = 0, description = "",
        doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    private val habitToServer = Habit(color = 0, count = 0, date = 0, description = "", frequency = 0,
        priority = 0, title = "", type = 0, uid = "")

    private val habitUID = HabitUID(uid = "")

    private val habitDone = HabitDone(0, "")

    private val flowResult = flow<List<HabitForLocal>> {}

    private val addHabitToLocalUseCase: AddHabitToLocalUseCase = mock()
    private val addHabitUseCase: AddHabitUseCase = mock()
    private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase = mock()
    private val deleteHabitUseCase: DeleteHabitUseCase = mock()
    private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase = mock()
    private val getHabitsUseCase: GetHabitsUseCase = mock()
    private val refreshHabitUseCase: RefreshHabitUseCase = mock()
    private val setHabitIsCompletedOnServerUseCase: SetHabitIsCompletedOnServerUseCase = mock()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test(expected = NullPointerException::class)
    fun `test use cases methods with coroutine`() = mainCoroutineRule.runBlockingTest {
        doNothing().`when`(addHabitToLocalUseCase).addHabitToLocal(habit)
        doNothing().`when`(addHabitUseCase).addHabit(habitToServer)
        doNothing().`when`(deleteHabitUseCase).deleteHabitUseCase(habitUID)
        whenever(getHabitsUseCase.getHabits()).thenReturn(Response.success(listOf()))
        doNothing().`when`(refreshHabitUseCase).updateHabit(habitToServer)
        doNothing().`when`(setHabitIsCompletedOnServerUseCase).setHabitIsCompletedOnServer(habitDone)
    }

    @Test(expected = NullPointerException::class)
    fun `test use cases methods without coroutine`() {
        doNothing().`when`(deleteAllFromLocalUseCase).deleteAllFromLocal()
        whenever(getHabitsFromLocalUseCase.getHabitsFromLocal("")).thenReturn(flowResult)
    }
}