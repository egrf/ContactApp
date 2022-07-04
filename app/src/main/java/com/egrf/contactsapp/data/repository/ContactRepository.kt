package com.egrf.contactsapp.data.repository

import com.egrf.contactsapp.data.database.ContactsDatabase
import com.egrf.contactsapp.data.network.ContactsApi
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Single
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val dataBase: ContactsDatabase,
    private val api: ContactsApi
): IContactRepository {

    override fun getAllContacts(): Single<List<Contact>> {
        TODO("Not yet implemented")
    }

}