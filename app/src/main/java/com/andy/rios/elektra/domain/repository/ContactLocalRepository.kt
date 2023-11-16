package com.andy.rios.elektra.domain.repository

import com.andy.rios.elektra.data.model.ContactResponse
import com.andy.rios.elektra.common.Either
import kotlinx.coroutines.flow.Flow

interface ContactLocalRepository {
    suspend fun saveContactIdLocal(contactResponse: ContactResponse): Either<Throwable, Boolean>
    suspend fun deleteContact(contactResponse: ContactResponse): Either<Throwable, ArrayList<ContactResponse>>
    suspend fun editContactIdLocal(contactResponse: ContactResponse): Either<Throwable, Boolean>
}