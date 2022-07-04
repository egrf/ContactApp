package com.egrf.contactsapp.ui.features.main.di

import androidx.lifecycle.ViewModel
import com.egrf.contactsapp.ui.di.annotations.ViewModelKey
import com.egrf.contactsapp.ui.features.main.MainFragment
import com.egrf.contactsapp.ui.features.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    fun inject(fragment: MainFragment)
}

@Module
interface MainFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(viewModel: MainViewModel): ViewModel

}