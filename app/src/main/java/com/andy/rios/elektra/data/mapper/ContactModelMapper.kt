package com.andy.rios.elektra.data.mapper

import com.andy.rios.elektra.data.model.ContactResponse
import com.andy.rios.elektra.domain.model.ContactModel
import com.andy.rios.elektra.room.entity.ContactDBEntity


fun ContactResponse.toDomain() = ContactModel(
    id = id,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age,
    phone = phone,
    gender = gender,
    img = img
)

fun ContactResponse.toDbData() = ContactDBEntity(
    id = id,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age,
    phone = phone,
    gender = gender,
    img = img
)

fun ContactDBEntity.toDbData() = ContactResponse(
    id = id,
    name = name,
    ape_pat = ape_pat,
    ape_mat = ape_mat,
    age = age,
    phone = phone,
    gender = gender,
    img = img
)