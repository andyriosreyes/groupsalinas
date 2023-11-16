package com.andy.rios.elektra.data.datasource

import com.andy.rios.elektra.room.dao.ContactDao
import com.andy.rios.elektra.room.entity.ContactDBEntity
import com.andy.rios.elektra.data.mapper.toDbData
import com.andy.rios.elektra.data.model.ContactResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactLocalDataSource(
    private val contactDao: ContactDao
) : ContactDataSource {

    override suspend fun saveContact(contactResponse: List<ContactResponse>) {
        withContext(Dispatchers.IO) {
            contactDao.saveContact(contactResponse.map {it.toDbData()})
        }
    }

    override suspend fun saveContactId(contactResponse: ContactResponse) {
        withContext(Dispatchers.IO) {
            contactDao.saveContactId(contactResponse.toDbData())
        }
    }

    override suspend fun deleteContact(contactResponse: ContactResponse): ArrayList<ContactResponse> {
        var allContact : List<ContactDBEntity> = arrayListOf()
        withContext(Dispatchers.IO) {
            contactDao.deleteContact(contactResponse.toDbData())
            allContact = contactDao.getContacts()
        }
        return allContact.map { it.toDbData() } as ArrayList<ContactResponse>
    }

    override suspend fun getAllContact(): ArrayList<ContactResponse> {
        var allContact : List<ContactDBEntity> = arrayListOf()
        withContext(Dispatchers.IO) {
            allContact = contactDao.getContacts()
        }
        return allContact.map { it.toDbData() } as ArrayList<ContactResponse>
    }

    override suspend fun editContactId(contactResponse: ContactResponse) {
        withContext(Dispatchers.IO) {
            val contact = contactResponse.toDbData()
            contactDao.editContact(contact.name?:"",contact.ape_pat?:"",contact.ape_mat?:"",contact.age,contact.phone?:"000000000",contact.gender?:"M",contact.id)
        }

    }


}