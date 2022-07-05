package com.egrf.contactsapp.domain.repository

import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Observable

interface IContactRepository {

    fun loadAllContacts(isFromDatabase: Boolean): Observable<List<Contact>>

    fun fetchContacts(): Observable<List<Contact>>
}
