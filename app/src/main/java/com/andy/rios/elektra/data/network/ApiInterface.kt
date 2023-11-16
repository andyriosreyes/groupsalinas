package com.andy.rios.elektra.data.network

import com.andy.rios.elektra.data.APIUrl.GET_CONTACT
import com.google.gson.JsonObject
import retrofit2.http.*

interface ApiInterface {

    @GET(GET_CONTACT)
    suspend fun getContacts(
    ): JsonObject

}