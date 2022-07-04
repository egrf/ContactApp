package com.egrf.contactsapp.ui

import android.app.Application
import com.egrf.contactsapp.ui.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.initAppComponent(this)
    }

}