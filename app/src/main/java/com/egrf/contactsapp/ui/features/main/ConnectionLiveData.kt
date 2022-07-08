package com.egrf.contactsapp.ui.features.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import com.egrf.contactsapp.ui.features.main.model.ConnectionModel
import com.egrf.contactsapp.ui.features.main.model.ConnectionType


class ConnectionLiveData(
    private val context: Context
) : LiveData<ConnectionModel>() {

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }

    private val networkReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetwork =
                    intent.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
                val isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    when (activeNetwork!!.type) {
                        ConnectivityManager.TYPE_WIFI -> postValue(
                            ConnectionModel(
                                ConnectionType.WIFI,
                                true
                            )
                        )
                        ConnectivityManager.TYPE_MOBILE -> postValue(
                            ConnectionModel(
                                ConnectionType.MOBILE,
                                true
                            )
                        )
                    }
                } else {
                    postValue(ConnectionModel(ConnectionType.NONE, false))
                }
            }
        }
    }
}
