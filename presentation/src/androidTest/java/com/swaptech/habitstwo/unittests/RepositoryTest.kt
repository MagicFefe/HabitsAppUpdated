package com.swaptech.habitstwo.unittests

import com.swaptech.data.models.HabitForLocal
import com.swaptech.data.repository.RepositoryImpl
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitDone
import com.swaptech.domain.models.HabitUID
import com.swaptech.habitstwo.utils.MainCoroutineRule
import com.swaptech.habitstwo.utils.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class RepositoryTest {

    private val habit = HabitForLocal(color = 0, count = 0, date = 0, description = "",
        doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")

    private val habitToServer = Habit(color = 0, count = 0, date = 0, description = "", frequency = 0,
        priority = 0, title = "", type = 0, uid = "")

    private val habitUID = HabitUID(uid = "")

    private val habitDone = HabitDone(0, "")

    private val repository: RepositoryImpl = mock() // RepositoryImpl(api, dao)

    private val flowResult = flow<List<HabitForLocal>> {}

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `test methods with coroutine`() = mainCoroutineRule.runBlockingTest {
        whenever(repository.getHabits()).thenReturn(Response.success(listOf()))
        whenever(repository.addHabit(habitToServer)).thenReturn(Response.success(200))
        whenever(repository.updateHabit(habitToServer)).thenReturn(Response.success(habitUID))
        whenever(repository.deleteHabit(habitUID)).thenReturn(Response.success(Unit))
        whenever(repository.setHabitIsCompletedOnServer(habitDone)).thenReturn(Response.success(200))
        whenever(repository.addHabitsToLocal(habit)).thenReturn(Unit)
        whenever(repository.updateHabitToLocal(habit)).thenReturn(Unit)
        whenever(repository.deleteHabitFromLocal(habit)).thenReturn(Unit)
    }

    @Test
    fun `test methods without coroutine`() {
        doNothing().`when`(repository).deleteAllFromLocal()
        whenever(repository.getHabitsFromLocal("")).thenReturn(flowResult)
    }
}