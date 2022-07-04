package com.egrf.contactsapp.ui.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egrf.contactsapp.ui.di.ViewModelFactory
import com.egrf.contactsapp.ui.di.annotations.ViewModelKey
import com.egrf.contactsapp.ui.features.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    internal abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}
