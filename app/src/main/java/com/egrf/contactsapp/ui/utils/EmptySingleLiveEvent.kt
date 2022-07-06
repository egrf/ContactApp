package com.egrf.contactsapp.ui.utils

import androidx.annotation.MainThread

class EmptySingleLiveEvent : SingleLiveEvent<Void?>() {
    @MainThread
    fun call() {
        value = null
    }

    fun postCall() {
        super.postValue(null)
    }
}