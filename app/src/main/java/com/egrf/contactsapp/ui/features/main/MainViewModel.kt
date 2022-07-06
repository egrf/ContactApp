package com.egrf.contactsapp.ui.features.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava2.cachedIn
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import com.egrf.contactsapp.domain.utils.PreferencesHelper
import com.egrf.contactsapp.ui.extensions.toImmutable
import com.egrf.contactsapp.ui.features.base.BaseViewModel
import com.egrf.contactsapp.ui.utils.EmptySingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val contactsInteractor: IContactsInteractor,
    private val sharedPrefs: PreferencesHelper
) : BaseViewModel() {

//    private val _contacts = MutableLiveData<List<Contact>>()
//    val contacts = _contacts.toImmutable()

    private val _clearContactListEvent = EmptySingleLiveEvent()
    val clearContactListEvent = _clearContactListEvent.toImmutable()

    private val _fetchContactsFromDb = MutableLiveData<Boolean>()
    val fetchContactsFromDb = _fetchContactsFromDb.toImmutable()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState.toImmutable()

    private lateinit var disposable: Disposable

    companion object {
        private const val LAST_UPDATE_TIME_KEY = "LAST_UPDATE_TIME_KEY"
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    init {
        loadContacts()
    }

    fun loadContacts() {
        _fetchContactsFromDb.value = false
        val lastUpdateTimeString = sharedPrefs.getString(LAST_UPDATE_TIME_KEY)
        return if (!lastUpdateTimeString.isNullOrBlank()) {
            val lastUpdateTime = LocalDateTime.parse(lastUpdateTimeString, formatter)
            if (LocalDateTime.now().isAfter(lastUpdateTime.plusMinutes(1))) {
                Log.d("YAYAYA", "getAllContacts from internet ")
                loadFromInternet()
            } else {
                Log.d("YAYAYA", "getAllContacts from the db: ")
                _fetchContactsFromDb.value = true
            }
        } else {
            Log.d("YAYAYA", "getAllContacts for the first time: ")
            loadFromInternet()
        }

    }

    private fun loadFromInternet() {
        disposable = contactsInteractor.getAllContacts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _loadingState.value = true
                _clearContactListEvent.call()
            }
            .doOnComplete {
                _loadingState.value = false
                sharedPrefs.putString(LAST_UPDATE_TIME_KEY, LocalDateTime.now().format(formatter))
                _fetchContactsFromDb.value = true
            }.subscribe({}, { error ->
                Log.d("YAYAYA", "loadContacts: ${error.cause}")
            })
    }

    fun fetchContacts() = contactsInteractor.getContacts().cachedIn(viewModelScope)

}

