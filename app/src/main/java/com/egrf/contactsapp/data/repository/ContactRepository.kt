package com.egrf.contactsapp.data.repository

import androidx.paging.PagingSource
import com.egrf.contactsapp.data.database.ContactsDatabase
import com.egrf.contactsapp.data.network.ContactsApi
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val dataBase: ContactsDatabase,
    private val api: ContactsApi
) : IContactRepository {

    private fun onNext(contacts: List<Contact>) {
        dataBase.contactDao().saveContacts(contacts)
    }

    override fun loadAllContacts(): Observable<List<Contact>> {
        return Observable.zip(
            api.getFromFirstSource().subscribeOn(Schedulers.io()),
            api.getFromSecondSource().subscribeOn(Schedulers.io()),
            api.getFromThirdSource().subscribeOn(Schedulers.io())
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

    override fun loadContactsFromDatabase(): PagingSource<Int, Contact> =
        dataBase.contactDao().getAllContacts()

    override fun searchContacts(searchText: String): PagingSource<Int, Contact> =
        dataBase.contactDao().searchContact("%$searchText%")

}
