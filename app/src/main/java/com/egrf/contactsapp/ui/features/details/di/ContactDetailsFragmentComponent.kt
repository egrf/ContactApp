package com.egrf.contactsapp.ui.features.details.di

import androidx.lifecycle.ViewModel
import com.egrf.contactsapp.ui.di.annotations.ViewModelKey
import com.egrf.contactsapp.ui.features.details.ContactDetailsFragment
import com.egrf.contactsapp.ui.features.details.ContactDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [ContactDetailsFragmentModule::class])
interface ContactDetailsFragmentComponent {
    fun inject(fragment: ContactDetailsFragment)
}

@Module
interface ContactDetailsFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    fun bindViewModel(viewModel: ContactDetailsViewModel): ViewModel

}
