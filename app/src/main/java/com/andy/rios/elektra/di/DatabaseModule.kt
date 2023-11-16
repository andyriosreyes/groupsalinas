package com.andy.rios.elektra.di

import android.content.Context
import androidx.room.Room
import com.andy.rios.elektra.room.GroupSalinaDataBase
import com.andy.rios.elektra.room.dao.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GroupSalinaDataBase {
        return Room.databaseBuilder(context, GroupSalinaDataBase::class.java, "contact.db").build()
    }

    @Provides
    fun provideDao(dataBase: GroupSalinaDataBase): ContactDao {
        return dataBase.contactDao()
    }

}