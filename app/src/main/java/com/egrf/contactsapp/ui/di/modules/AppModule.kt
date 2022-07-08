package com.egrf.contactsapp.ui.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.egrf.contactsapp.data.database.ContactsDatabase
import com.egrf.contactsapp.data.repository.ContactRepository
import com.egrf.contactsapp.domain.interactors.ContactsInteractor
import com.egrf.contactsapp.domain.interactors.IContactsInteractor
import com.egrf.contactsapp.domain.repository.IContactRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.InnerAppModule::class])
class AppModule(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "contact_app_prefs"
    }

    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideDatabaseStorage(): ContactsDatabase = ContactsDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Module
    interface InnerAppModule {

        @Binds
        @Singleton
        fun provideRepository(repository: ContactRepository): IContactRepository

        @Binds
        @Singleton
        fun provideContactInteractor(contactInteractor: ContactsInteractor): IContactsInteractor
    }
}
