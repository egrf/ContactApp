package com.egrf.contactsapp.ui.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import com.egrf.contactsapp.domain.utils.PreferencesHelper
import com.egrf.contactsapp.ui.extensions.EMPTY
import com.egrf.contactsapp.ui.extensions.toImmutable
import com.egrf.contactsapp.ui.features.base.BaseViewModel
import com.egrf.contactsapp.ui.features.main.model.ConnectionStatus
import com.egrf.contactsapp.ui.utils.EmptySingleLiveEvent
import io.reactivex.Flowable
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

    private val _clearContactListEvent = EmptySingleLiveEvent()
    val clearContactListEvent = _clearContactListEvent.toImmutable()

    private val _loadFromDbEvent = EmptySingleLiveEvent()
    val loadFromDbEvent = _loadFromDbEvent.toImmutable()

    private val _loadingErrorEvent = EmptySingleLiveEvent()
    val loadingErrorEvent = _loadingErrorEvent.toImmutable()

    private val _fetchContactsFromDb = MutableLiveData<Boolean>()
    val fetchContactsFromDb = _fetchContactsFromDb.toImmutable()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState.toImmutable()

    private var connectionStatus = ConnectionStatus.NONE
    private var isInited = false
    private var disposable: Disposable? = null
    var lastSearchQuery = String.EMPTY

    companion object {
        private const val LAST_UPDATE_TIME_KEY = "LAST_UPDATE_TIME_KEY"
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    fun init() {
        if (!isInited) {
            loadContacts()
            isInited = true
        }
    }

    fun loadContacts() {
        _fetchContactsFromDb.value = false
        val lastUpdateTimeString = sharedPrefs.getString(LAST_UPDATE_TIME_KEY)
        return if (!lastUpdateTimeString.isNullOrBlank()) {
            val lastUpdateTime = LocalDateTime.parse(lastUpdateTimeString, formatter)
            if (LocalDateTime.now().isAfter(lastUpdateTime.plusMinutes(1))) {
                loadFromInternet()
            } else {
                if (connectionStatus == ConnectionStatus.NONE) {
                    _loadFromDbEvent.call()
                }
                _fetchContactsFromDb.value = true
            }
        } else {
            loadFromInternet()
        }

    }

    private fun loadFromInternet() {
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            disposable = contactsInteractor.getAllContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _loadingState.value = true
                    _clearContactListEvent.call()
                }
                .doOnComplete {
                    _loadingState.value = false
                    sharedPrefs.putString(
                        LAST_UPDATE_TIME_KEY,
                        LocalDateTime.now().format(formatter)
                    )
                    _fetchContactsFromDb.value = true
                }.subscribe({}, {
                    _loadingErrorEvent.call()
                })
        } else {
            _loadFromDbEvent.call()
            _fetchContactsFromDb.value = true
        }

    }

    fun searchContacts(searchText: String): Flowable<PagingData<Contact>> {
        lastSearchQuery = searchText
        _fetchContactsFromDb.value = false
        return contactsInteractor.searchContacts(searchText).cachedIn(viewModelScope)
    }

    fun fetchAllContacts(): Flowable<PagingData<Contact>> {
        lastSearchQuery = String.EMPTY
        return contactsInteractor.getContacts().cachedIn(viewModelScope)
    }

    fun setConnectionStatus(connectionStatus: ConnectionStatus) {
        this.connectionStatus = connectionStatus
    }

    fun disposeLoading() {
        disposable?.dispose()
    }
}

