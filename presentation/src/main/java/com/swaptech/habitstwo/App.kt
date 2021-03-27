package com.swaptech.habitstwo

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.swaptech.data.di.ContextModule
import com.swaptech.data.models.HabitForLocal
import com.swaptech.habitstwo.components.ApplicationComponent
import com.swaptech.habitstwo.components.DaggerApplicationComponent

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
    }
}