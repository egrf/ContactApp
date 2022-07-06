package com.egrf.contactsapp.ui.di

import android.content.Context
import com.egrf.contactsapp.ui.di.components.AppComponent
import com.egrf.contactsapp.ui.di.components.DaggerAppComponent
import com.egrf.contactsapp.ui.di.modules.AppModule
import com.egrf.contactsapp.ui.di.modules.RestModule
import com.egrf.contactsapp.ui.features.details.di.ContactDetailsFragmentComponent
import com.egrf.contactsapp.ui.features.main.di.MainFragmentComponent

object Injector {

    private lateinit var appComponent: AppComponent


    val mainFragmentComponent: MainFragmentComponent
        get() {
            return appComponent.mainFragmentComponent
        }

    val contactDetailsFragmentComponent: ContactDetailsFragmentComponent
    get() {
        return appComponent.contactDetailsFragmentComponent
    }

    fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .restModule(RestModule())
            .build()
    }

}
