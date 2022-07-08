package com.egrf.contactsapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egrf.contactsapp.domain.entity.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContacts(contacts: List<Contact>)

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    fun getAllContacts(): PagingSource<Int, Contact>

    @Query("DELETE FROM contact_table")
    fun clearAll()

    @Query("SELECT * FROM contact_table WHERE name LIKE :searchText OR phone LIKE :searchText")
    fun searchContact(searchText: String): PagingSource<Int, Contact>

    @Query("SELECT * FROM contact_table WHERE name LIKE :searchText OR phone LIKE :searchText")
    fun searchContactList(searchText: String): List<Contact>
}
