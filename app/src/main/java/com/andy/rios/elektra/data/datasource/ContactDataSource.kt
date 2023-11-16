package com.andy.rios.elektra.data.datasource

import com.andy.rios.elektra.data.model.ContactResponse

interface ContactDataSource {
    suspend fun saveContact(contactModel: List<ContactResponse>)
    suspend fun saveContactId(contactResponse: ContactResponse)
    suspend fun deleteContact(contactResponse: ContactResponse) : ArrayList<ContactResponse>
    suspend fun getAllContact() : ArrayList<ContactResponse>
    suspend fun editContactId(contactResponse: ContactResponse)
}