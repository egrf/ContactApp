package com.egrf.contactsapp.domain.repository

import androidx.paging.PagingSource
import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Observable

interface IContactRepository {

    fun loadAllContacts(): Observable<List<Contact>>

    fun loadContactsFromDatabase(): PagingSource<Int, Contact>

    fun searchContacts(searchText: String): PagingSource<Int, Contact>

}
