package com.egrf.contactsapp.data.network

import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Observable
import retrofit2.http.GET

interface ContactsApi {

    @GET("generated-01.json")
    fun getFromFirstSource(): Observable<List<Contact>>

    @GET("generated-02.json")
    fun getFromSecondSource(): Observable<List<Contact>>

    @GET("generated-03.json")
    fun getFromThirdSource(): Observable<List<Contact>>

}
