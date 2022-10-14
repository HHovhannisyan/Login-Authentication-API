package com.example.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import com.example.data.network.Resource
import com.example.databinding.ActivityMainBinding
import com.example.ui.enable
import com.example.ui.handleApiError
import com.example.ui.home.HomeActivity
import com.example.ui.visible
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.Key
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var resp: Int = 0


    private lateinit var encrypted: String
    private val publicKey: String =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxDV6Tkh5noY4NWUd5G3qmdLkhBCRwwG06buiCc34Xyshtc8eRMaLZarHeQpEkPk43f7Xo7UhOekqQ1+/JlZOrCKRUb8VPdLv/gA9lxzDAJDuSTN8RY0BBJlwNjrP3nrDLIfxAe5OZXxgdwvSi5RxnghWaiM7qtrcaAKcnZnoNrHXvouw9CbLEJa92RJjKIUE94GbFozDSM62CokXrKC9RgcwZfLQijzlgKM90pjLjAlw1Iz/IN3g5k+TqrbHX7zo8I85MIn/1yRyUIGMT3BUMiPKRkCqVZ1NGPLLsuz4vCe/wA9dmhZxrEAECLEFp6/9PoT9abcIxIYJpV1PhxeG1wIDAQAB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        binding.editTextTextPassword.addTextChangedListener {
            val clientIdentifier = binding.edittxtClientIdentifier.text.toString().trim()
            val pass = binding.editTextTextPassword.text.toString().trim()

            binding.buttonLogin.enable(
                clientIdentifier.isNotEmpty() && pass.isNotEmpty() && it.toString().isNotEmpty()
            )
        }


        binding.buttonLogin.setOnClickListener {
            val clientIdentifier = binding.edittxtClientIdentifier.text.toString().trim()
            val pass = binding.editTextTextPassword.text.toString().trim()
            val rootObject = JSONObject()
            rootObject.put("ClientIdentifier", clientIdentifier)
            rootObject.put("Password", pass)
            encrypted = encrypt(rootObject.toString(), publicKey)

            Log.d("LogFrag", "encrypted:\t $encrypted")

            val partner = viewModel.getPartnerId(applicationContext).toString()

            viewModel.login(clientIdentifier, partner, pass, encrypted)
            Log.d("LogFrag", "rootObject$rootObject")


            viewModel.loginResponse.observe(this, {

                binding.progressbar.visible(it is Resource.Loading)

                when (it) {
                    is Resource.Success -> {
                        resp = it.value.responseCode
                        Log.d("LogFrag", "responseCode:\t $resp")

                        val token = it.value.token

                        Log.d("LogFrag", "token:\t $token")


                        val jsonObject = JSONObject()
                        try {
                            jsonObject.put("Id", it.value.id)
                            jsonObject.put("Email", it.value.email)
                            jsonObject.put("IsEmailVerified", it.value.isEmailVerified)
                            jsonObject.put("CurrencyId", it.value.currencyId)
                            jsonObject.put("UserName", it.value.userName)
                            jsonObject.put("Password", it.value.password)
                            jsonObject.put("PartnerId", it.value.partnerId)
                            jsonObject.put("RegionId", it.value.regionId)
                            jsonObject.put("CountryId", it.value.countryId)
                            jsonObject.put("Gender", it.value.gender)
                            jsonObject.put("BirthDate", it.value.birthDate)
                            jsonObject.put("FirstName", it.value.firstName)
                            jsonObject.put("LastName", it.value.lastName)
                            jsonObject.put("DocumentNumber", it.value.documentNumber)
                            jsonObject.put("PhoneNumber", it.value.phoneNumber)
                            jsonObject.put("DocumentIssuedBy", it.value.documentIssuedBy)
                            jsonObject.put("Address", it.value.address)
                            jsonObject.put("MobileNumber", it.value.mobileNumber)
                            jsonObject.put(
                                "IsMobileNumberVerified",
                                it.value.isMobileNumberVerified
                            )
                            jsonObject.put("LanguageId", it.value.languageId)
                            jsonObject.put("RegistrationIp", it.value.registrationIp)
                            jsonObject.put("CreationTime", it.value.creationTime)
                            jsonObject.put("LastUpdateTime", it.value.lastUpdateTime)
                            jsonObject.put("Token", it.value.token)
                            jsonObject.put("EmailOrMobile", it.value.emailOrMobile)
                            jsonObject.put("SendMail", it.value.sendMail)
                            jsonObject.put("SendSms", it.value.sendSms)
                            jsonObject.put("SendPromotions", it.value.sendPromotions)
                            jsonObject.put("IsDocumentVerified", it.value.isDocumentVerified)
                            jsonObject.put("ZipCode", it.value.zipCode)
                            jsonObject.put("Info", it.value.info)
                            jsonObject.put("CategoryId", it.value.categoryId)
                            jsonObject.put("CategoryName", it.value.categoryName)
                            jsonObject.put(
                                "WelcomeBonusActivationKey",
                                it.value.welcomeBonusActivationKey
                            )
                            jsonObject.put("CurrencySymbol", it.value.currencySymbol)
                            jsonObject.put("LastLogin", it.value.lastLogin)
                            jsonObject.put("LastLogout", it.value.lastLogout)
                            jsonObject.put("LastLoginIp", it.value.lastLoginIp)
                            jsonObject.put("ResetPassword", it.value.resetPassword)
                            jsonObject.put("ResetNickName", it.value.resetNickName)
                            jsonObject.put("AcceptTermsConditions", it.value.acceptTermsConditions)
                            jsonObject.put(
                                "DocumentExpirationStatus",
                                it.value.documentExpirationStatus
                            )
                            jsonObject.put("AD", it.value.aD)
                            jsonObject.put("ADM", it.value.aDM)
                            jsonObject.put("LoginClient", it.value.loginClient)
                            jsonObject.put("ResponseCode", it.value.responseCode)
                            jsonObject.put("Description", it.value.description)
                            jsonObject.put("ResponseObject", it.value.responseObject)
                        }catch (e: JSONException){
                            e.printStackTrace()
                        }



                        if (resp == 0) {

                            intent = Intent(this, HomeActivity::class.java)
                            Log.d("LogFrag", "token:\t $token")
                            intent.putExtra("token", token)
                            intent.putExtra("responseBody", jsonObject.toString())
                            intent.putExtra("encr", encrypted)
                            intent.putExtra("clientId", clientIdentifier)
                            intent.putExtra("password", pass)

                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this,
                                it.value.description,
                                Toast.LENGTH_LONG
                            ).show()

                            Snackbar.make(
                                window.decorView.rootView,
                                it.value.description,
                                Snackbar.LENGTH_LONG
                            ).show()


                            Log.d("LogFrag", "encrypted invalid:\t $encrypted")

                        }
                    }

                    is Resource.Failure -> {
                        handleApiError(it)
                        Log.d("LoginFragment", "Failure!!")
                    }
                }
            })
        }
    }


    @Throws(GeneralSecurityException::class, IOException::class)
    fun loadPublicKey(stored: String): Key {
        val data: ByteArray = Base64.decode(stored, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(data)
        val fact = KeyFactory.getInstance("RSA")
        return fact.generatePublic(spec)
    }


    @Throws(java.lang.Exception::class)
    fun encrypt(plainText: String, publicKey: String): String {
        val encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        encryptCipher.init(Cipher.ENCRYPT_MODE, loadPublicKey(publicKey))
        val cipherText = encryptCipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

}
