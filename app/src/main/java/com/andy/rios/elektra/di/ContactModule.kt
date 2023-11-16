package com.andy.rios.elektra.di

import com.andy.rios.elektra.data.datasource.ContactLocalDataSource
import com.andy.rios.elektra.data.network.ApiInterface
import com.andy.rios.elektra.data.repository.ContactLocalRepositoryImp
import com.andy.rios.elektra.data.repository.ContactRepositoryImp
import com.andy.rios.elektra.domain.repository.ContactLocalRepository
import com.andy.rios.elektra.domain.repository.ContactRepository
import com.andy.rios.elektra.domain.usecase.GetContactUseCase
import com.andy.rios.elektra.domain.usecase.GetDeleteContactLocalUseCase
import com.andy.rios.elektra.domain.usecase.GetEditContactLocalUseCase
import com.andy.rios.elektra.domain.usecase.SaveContactIdLocalUseCase
import com.andy.rios.elektra.room.dao.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContactModule {

    @Provides
    @Singleton
    fun provideContactRepositoryImp(
        apiInterface: ApiInterface,
        contactLocalDataSource: ContactLocalDataSource
    ): ContactRepository {
        return ContactRepositoryImp(apiInterface,contactLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideContactLocalRepositoryImp(
        contactLocalDataSource: ContactLocalDataSource
    ): ContactLocalRepository {
        return ContactLocalRepositoryImp(contactLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideGetContactUseCase(contactRepository: ContactRepository): GetContactUseCase {
        return GetContactUseCase(contactRepository)
    }


    @Provides
    @Singleton
    fun provideSaveContactLocalIdUseCase(contactLocalRepository: ContactLocalRepository): SaveContactIdLocalUseCase {
        return SaveContactIdLocalUseCase(contactLocalRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteContactLocalUseCase(contactLocalRepository: ContactLocalRepository): GetDeleteContactLocalUseCase {
        return GetDeleteContactLocalUseCase(contactLocalRepository)
    }

    @Provides
    @Singleton
    fun provideEditContactLocalUseCase(contactLocalRepository: ContactLocalRepository): GetEditContactLocalUseCase {
        return GetEditContactLocalUseCase(contactLocalRepository)
    }

    @Provides
    @Singleton
    fun provideContactLocalDataSource(
        contactDao: ContactDao,
    ): ContactLocalDataSource {
        return ContactLocalDataSource(contactDao)
    }
}