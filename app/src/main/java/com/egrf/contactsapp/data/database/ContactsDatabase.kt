package com.egrf.contactsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egrf.contactsapp.data.database.dao.ContactDao
import com.egrf.contactsapp.domain.entity.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        private const val CONTACTS_DB = "contacts_database"

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    CONTACTS_DB
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
