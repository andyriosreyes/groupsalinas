package com.andy.rios.elektra.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactDBEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val ape_pat: String?,
    val ape_mat: String?,
    val age: Int,
    val phone: String?,
    val gender: String?,
    val img : String?
)