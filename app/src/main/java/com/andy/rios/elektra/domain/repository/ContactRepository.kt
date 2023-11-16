package com.andy.rios.elektra.domain.repository

import com.andy.rios.elektra.data.model.ContactResponse
import com.andy.rios.elektra.common.Either

interface ContactRepository {
    suspend fun getContacts(): Either<Throwable, ArrayList<ContactResponse>>
}