package com.egrf.contactsapp.domain.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.repository.IContactRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface IContactsInteractor {

    fun getAllContacts(forceRefresh: Boolean = false): Observable<List<Contact>>

    fun getContacts(): Flowable<PagingData<Contact>>

    fun searchContacts(searchText: String): Flowable<PagingData<Contact>>

}

class ContactsInteractor @Inject constructor(
    private val repository: IContactRepository
) : IContactsInteractor {

    companion object {
        private const val PAGE_SIZE = 50
        private const val MAX_SIZE = 300
        private const val PREFETCH_SIZE = 20
    }

    override fun getAllContacts(forceRefresh: Boolean): Observable<List<Contact>> =
        repository.loadAllContacts().subscribeOn(Schedulers.io())


    override fun getContacts(): Flowable<PagingData<Contact>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = PREFETCH_SIZE,
                maxSize = MAX_SIZE
            )
        ) {
            repository.loadContactsFromDatabase()
        }.flowable


    override fun searchContacts(searchText: String): Flowable<PagingData<Contact>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = PREFETCH_SIZE,
                maxSize = MAX_SIZE
            )
        ) {
            repository.searchContacts(searchText)
        }.flowable

}
