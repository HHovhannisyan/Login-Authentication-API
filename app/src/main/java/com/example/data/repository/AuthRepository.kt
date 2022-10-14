package com.example.data.repository

import android.content.Context
import android.util.Log
import net.example.data.network.AuthApi
import net.example.data.network.EncyptedData
import net.example.data.network.SafeApiCall
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi
) : SafeApiCall {

    suspend fun login(
        partnerId: String,
        encyptedData: EncyptedData
    ) = safeApiCall {
        api.login(partnerId, encyptedData)
    }

    fun getPartnerId(context: Context): Int {
        val partnerId: Int
        val bufferReader = context.assets?.open("user.json")?.bufferedReader()
        val jsonString = bufferReader.use {
            it?.readText()
        }

        val jsonObject = JSONObject(jsonString!!)
        partnerId = jsonObject.getString("PartnerId").toInt()

        Log.d("getPartnerId()", "partnerid: $partnerId \n")

        return partnerId
    }


    fun getPartnerSite(context: Context): String {
        val partnerSite: String
        val bufferReader = context.assets?.open("user.json")?.bufferedReader()
        val jsonString = bufferReader.use {
            it?.readText()
        }
        val jsonObject = JSONObject(jsonString!!)
        partnerSite = jsonObject.getString("PartnerSite")

        Log.d("getPartnerSite()", "PartnerSite: $partnerSite \n")

        return partnerSite
    }
}
