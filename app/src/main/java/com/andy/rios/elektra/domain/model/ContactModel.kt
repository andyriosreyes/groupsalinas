package com.andy.rios.elektra.domain.model

data class ContactModel(
    val id: Int? = null,
    val name: String?,
    val ape_pat: String?,
    val ape_mat: String?,
    val age: Int,
    val phone: String?,
    val gender: String?,
    val img : String?
)