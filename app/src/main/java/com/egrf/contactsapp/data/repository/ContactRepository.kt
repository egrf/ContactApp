package com.egrf.contactsapp.data.repository

import com.egrf.contactsapp.data.database.ContactsDatabase
import com.egrf.contactsapp.data.network.ContactsApi
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val dataBase: ContactsDatabase,
    private val api: ContactsApi
) : IContactRepository {

    private val subject = BehaviorSubject.create<List<Contact>>()

    fun clearContacts() = Single.just {
        subject.onNext(emptyList())
        dataBase.contactDao().clearAll()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    private fun onNext(contacts: List<Contact>) {
        subject.onNext(contacts)
        dataBase.contactDao().saveContacts(contacts)
    }

    private val contactList: Observable<List<Contact>>
        get() = subject

    override fun loadAllContacts(isFromDatabase: Boolean): Observable<List<Contact>> {
        return Observable.zip(
            getContactsFromFirstSource().subscribeOn(Schedulers.io()),
            getContactsFromSecondSource().subscribeOn(Schedulers.io()),
            getContactsFromThirdSource().subscribeOn(Schedulers.io())
        ) { questions, answers, favorites ->
            saveContacts(questions, answers, favorites)
        }
    }

    private fun saveContacts(
        t1: List<Contact>,
        t2: List<Contact>,
        t3: List<Contact>
    ): List<Contact> {
        val list = t1 + t2 + t3
        onNext(list)
        return list
    }


    private fun getContactsFromFirstSource(): Observable<List<Contact>> {
        return api.getFromFirstSource()
    }

    private fun getContactsFromSecondSource(): Observable<List<Contact>> {
        return api.getFromSecondSource()
    }

    private fun getContactsFromThirdSource(): Observable<List<Contact>> {
        return api.getFromThirdSource()
    }

    fun loadContactsFromDatabase() = dataBase.contactDao().getAllContacts()
        .subscribe { list -> onNext(list) }

    override fun fetchContacts() = contactList
}
