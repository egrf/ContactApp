package com.egrf.contactsapp.ui.features.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import com.egrf.contactsapp.ui.extensions.toImmutable
import com.egrf.contactsapp.ui.features.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val contactsInteractor: IContactsInteractor
) : BaseViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts = _contacts.toImmutable()

    @SuppressLint("CheckResult")
    fun loadContacts() {
        contactsInteractor.getAllContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                contactsInteractor.fetchContacts().subscribe {
                    _contacts.value = it
                }
            }
            .doOnComplete { }
            .subscribe()

    }


}
