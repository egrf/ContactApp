package com.egrf.contactsapp.ui.features.main

import androidx.lifecycle.MutableLiveData
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import com.egrf.contactsapp.ui.extensions.toImmutable
import com.egrf.contactsapp.ui.features.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val contactsInteractor: IContactsInteractor
) : BaseViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts = _contacts.toImmutable()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState.toImmutable()

    private lateinit var disposable: Disposable

    init {
        loadContacts()
        fetchContacts()
    }

    fun loadContacts() {
        contactsInteractor.getAllContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _loadingState.value = true
            }
            .doOnComplete {
                _loadingState.value = false
            }
            .subscribe()

    }

    private fun fetchContacts() {
        disposable = contactsInteractor.fetchContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _contacts.value = it
            }
    }


}
