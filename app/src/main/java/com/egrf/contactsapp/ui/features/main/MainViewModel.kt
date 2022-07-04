package com.egrf.contactsapp.ui.features.main

import androidx.lifecycle.ViewModel
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val contactsInteractor: IContactsInteractor
) : ViewModel() {

    init {
        loadContacts()
    }

    private fun loadContacts() {

    }

}
