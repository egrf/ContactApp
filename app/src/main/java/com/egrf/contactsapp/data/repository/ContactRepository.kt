package com.egrf.contactsapp.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.egrf.contactsapp.data.database.ContactsDatabase
import com.egrf.contactsapp.data.network.ContactsApi
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val dataBase: ContactsDatabase,
    private val api: ContactsApi
) : IContactRepository {

//    private val subject = BehaviorSubject.create<List<Contact>>()

    fun clearContacts() = Single.just {
//        subject.onNext(emptyList())
        dataBase.contactDao().clearAll()
    }
        .subscribeOn(Schedulers.io())

    private fun onNext(contacts: List<Contact>) {
//        subject.onNext(contacts)
        Log.d("YAYAYA", "onNext: ")
        dataBase.contactDao().saveContacts(contacts)
    }

//    private val contactList: Observable<List<Contact>>
//        get() = subject

    override fun loadAllContacts(): Observable<List<Contact>> {
        return Observable.zip(
            getContactsFromFirstSource().subscribeOn(Schedulers.io()),
            getContactsFromSecondSource().subscribeOn(Schedulers.io()),
            getContactsFromThirdSource().subscribeOn(Schedulers.io())
        ) { firstList, secondList, thirdList ->
            saveContacts(firstList, secondList, thirdList)
        }
    }

    private fun saveContacts(
        firstList: List<Contact>,
        secondList: List<Contact>,
        thirdList: List<Contact>
    ): List<Contact> {
        val finalList = firstList + secondList + thirdList
        onNext(finalList)
        return finalList
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

    override fun loadContactsFromDatabase(): PagingSource<Int, Contact> =
        dataBase.contactDao().getAllContacts()

//    override fun fetchContacts() = contactList
}
