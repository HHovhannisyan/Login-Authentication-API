package com.example.data.network

import com.example.data.responses.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

   @POST("{PartnerId}/api/Main/LoginClient")
    suspend fun login(
        @Path("PartnerId") partnerId: String,
        @Body encryptedData: EncyptedData
    ): LoginResponse
}


