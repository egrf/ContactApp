package com.egrf.contactsapp.ui.di.components

import android.content.Context
import com.egrf.contactsapp.ui.di.modules.AppModule
import com.egrf.contactsapp.ui.di.modules.RestModule
import com.egrf.contactsapp.ui.di.modules.ViewModelModule
import com.egrf.contactsapp.ui.features.main.di.MainFragmentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RestModule::class, ViewModelModule::class])
interface AppComponent {

    val context: Context

    val mainFragmentComponent: MainFragmentComponent

}
