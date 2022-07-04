package com.egrf.contactsapp.domain.repository

import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Single

interface IContactRepository {

    fun getAllContacts(): Single<List<Contact>>

}
