package com.egrf.contactsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Observable

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContacts(contacts: List<Contact>)

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    fun getAllContacts(): Observable<List<Contact>>

    @Query("DELETE FROM contact_table")
    fun clearAll()
}
