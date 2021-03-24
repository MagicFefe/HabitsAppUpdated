package com.swaptech.habitstwo

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.swaptech.data.di.ContextModule
import com.swaptech.data.models.HabitForLocal

class App : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set
    override fun onCreate() {
        super.onCreate()
        res = resources
        applicationComponent = DaggerApplicationComponent.builder().contextModule(ContextModule(this)).build()
    }

    companion object {
        //Access to string resources in code
        var res: Resources? = null
            private set

        //Receiving connection state
        var isConnected = MutableLiveData(false)

        //Exchanging data between fragments
        var item: HabitForLocal = HabitForLocal(color = 0, count = 0, date = 0, description = "",
                doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")
    }
}