package com.swaptech.habitstwo

import android.app.Application
import android.content.res.Resources
import com.swaptech.habitstwo.model.RecItem


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources

    }

    companion object {
        var instance: App? = null
            private set
        var res: Resources? = null
            private set

        var isConnected = false

        var item: RecItem = RecItem("", "", "", "", "", -1, 0,0 ,0)


    }

}