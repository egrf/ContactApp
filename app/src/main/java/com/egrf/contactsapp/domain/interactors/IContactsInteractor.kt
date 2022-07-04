package com.egrf.contactsapp.domain.interactors

import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Single
import javax.inject.Inject

interface IContactsInteractor {

    fun getAllContacts(): Single<List<Contact>>

}

class ContactsInteractor @Inject constructor(
    private val repository: IContactRepository
) : IContactsInteractor {

    override fun getAllContacts(): Single<List<Contact>> {
        TODO("Not yet implemented")
    }

}
