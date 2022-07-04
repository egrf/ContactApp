package com.egrf.contactsapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egrf.contactsapp.domain.entity.Contact
import io.reactivex.Single

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContacts(contacts: List<Contact>)

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    fun getAllContacts(): Single<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE id = :contactId")
    fun getContactById(contactId: String): Single<Contact>

    @Query("DELETE FROM contact_table")
    fun clearAll()
}
