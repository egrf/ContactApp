package com.egrf.contactsapp.ui.di

import android.content.Context
import com.egrf.contactsapp.ui.di.components.AppComponent
import com.egrf.contactsapp.ui.di.components.DaggerAppComponent
import com.egrf.contactsapp.ui.di.modules.AppModule
import com.egrf.contactsapp.ui.di.modules.RestModule

object Injector {

    private lateinit var appComponent: AppComponent

    internal fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .restModule(RestModule())
            .build()
    }

}
