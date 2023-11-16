package com.andy.rios.elektra.data.model

import com.google.gson.annotations.SerializedName

data class ContactResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ape_pat")
    val ape_pat: String?,
    @SerializedName("ape_mat")
    val ape_mat: String?,
    @SerializedName("age")
    val age: Int,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("img")
    val img : String?
)