package com.andy.rios.elektra.ui.model

import android.os.Parcelable
import com.andy.rios.elektra.domain.model.ContactModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int? = null,
    val name: String? = null,
    val ape_pat: String? = null,
    val ape_mat: String? = null,
    val age: Int? = 0,
    val phone: String? = null,
    val gender: String? = null,
    val img : String? = null
): Parcelable

fun Contact.toPresentation() = ContactModel(
    id = id,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age?:0,
    phone = phone,
    gender = gender,
    img = img
)