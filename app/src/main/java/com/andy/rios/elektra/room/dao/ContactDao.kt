package com.andy.rios.elektra.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andy.rios.elektra.room.entity.ContactDBEntity
import retrofit2.http.DELETE

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY id")
    fun getContacts(): List<ContactDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContact(contacts: List<ContactDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContactId(contact: ContactDBEntity)

    @Delete
    suspend fun deleteContact(contact: ContactDBEntity)

    @Query("Update contact set name=:name,ape_pat=:apePat,ape_mat=:apeMat,age=:age,phone=:phone,gender=:gender  WHERE id LIKE :id")
    suspend fun editContact(name: String,apePat : String,apeMat : String,age : Int,phone : String,gender : String,id : Int)

}