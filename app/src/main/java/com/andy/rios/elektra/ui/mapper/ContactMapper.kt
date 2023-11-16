package com.andy.rios.elektra.ui.mapper

import com.andy.rios.elektra.data.model.ContactResponse
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.ui.model.Contact


fun ContactModel.toPresentation() = Contact(
    id = id,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age?:0,
    phone = phone,
    gender = gender,
    img = img
)

fun ContactModel.toData() = ContactResponse(
    id = id?:0,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age?:0,
    phone = phone,
    gender = gender,
    img = img
)