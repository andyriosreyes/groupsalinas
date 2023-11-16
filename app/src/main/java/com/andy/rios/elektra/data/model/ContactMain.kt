package com.andy.rios.elektra.data.model

import com.google.gson.annotations.SerializedName

data class ContactMain(
    @SerializedName("contact")
    val contact : ArrayList<ContactResponse>?= null
)