package com.swaptech.habitstwo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import com.swaptech.habitstwo.App.Companion.isConnected

class CommonNetworkReceiver: BroadcastReceiver() {
    companion object {
        var connectionState = false
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        checkConnection(context!!)

    }
    private fun checkConnection(context: Context):Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionState : Boolean = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        Companion.connectionState = connectionState
        isConnected = connectionState

        return connectionState
    }
    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }
}