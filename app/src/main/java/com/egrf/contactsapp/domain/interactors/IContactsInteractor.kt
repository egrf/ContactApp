package com.egrf.contactsapp.domain.interactors

import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface IContactsInteractor {

    fun getAllContacts(forceRefresh: Boolean = false): Observable<List<Contact>>

    fun fetchContacts(): Observable<List<Contact>>

}

class ContactsInteractor @Inject constructor(
    private val repository: IContactRepository
) : IContactsInteractor {

    override fun getAllContacts(forceRefresh: Boolean) =
        repository.loadAllContacts(forceRefresh)

    override fun fetchContacts() = repository.fetchContacts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}
