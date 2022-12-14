package com.example.data.responses

import com.google.gson.annotations.SerializedName


data class  LoginResponse(
    @SerializedName("Id") val id : Int,
    @SerializedName("Email") val email : String,
    @SerializedName("IsEmailVerified") val isEmailVerified : Boolean,
    @SerializedName("CurrencyId") val currencyId : String,
    @SerializedName("UserName") val userName : String,
    @SerializedName("Password") val password : String,
    @SerializedName("PartnerId") val partnerId : Int,
    @SerializedName("RegionId") val regionId : Int,
    @SerializedName("CountryId") val countryId : Int,
    @SerializedName("Gender") val gender : Int,
    @SerializedName("BirthDate") val birthDate : String,
    @SerializedName("FirstName") val firstName : String,
    @SerializedName("LastName") val lastName : String,
    @SerializedName("DocumentNumber") val documentNumber : String,
    @SerializedName("PhoneNumber") val phoneNumber : String,
    @SerializedName("DocumentIssuedBy") val documentIssuedBy : String,
    @SerializedName("Address") val address : String,
    @SerializedName("MobileNumber") val mobileNumber : String,
    @SerializedName("IsMobileNumberVerified") val isMobileNumberVerified : Boolean,
    @SerializedName("LanguageId") val languageId : String,
    @SerializedName("RegistrationIp") val registrationIp : String,
    @SerializedName("CreationTime") val creationTime : String,
    @SerializedName("LastUpdateTime") val lastUpdateTime : String,
    @SerializedName("Token") val token : String,
    @SerializedName("EmailOrMobile") val emailOrMobile : String,
    @SerializedName("SendMail") val sendMail : Boolean,
    @SerializedName("SendSms") val sendSms : Boolean,
    @SerializedName("CallToPhone") val callToPhone : Boolean,
    @SerializedName("SendPromotions") val sendPromotions : Boolean,
    @SerializedName("IsDocumentVerified") val isDocumentVerified : Boolean,
    @SerializedName("ZipCode") val zipCode : String,
    @SerializedName("Info") val info : String,
    @SerializedName("CategoryId") val categoryId : Int,
    @SerializedName("CategoryName") val categoryName : String,
    @SerializedName("WelcomeBonusActivationKey") val welcomeBonusActivationKey : String,
    @SerializedName("CurrencySymbol") val currencySymbol : String,
    @SerializedName("LastLogin") val lastLogin : String,
    @SerializedName("LastLogout") val lastLogout : String,
    @SerializedName("LastLoginIp") val lastLoginIp : String,
    @SerializedName("ResetPassword") val resetPassword : Boolean,
    @SerializedName("ResetNickName") val resetNickName : String,
    @SerializedName("AcceptTermsConditions") val acceptTermsConditions : Boolean,
    @SerializedName("DocumentExpirationStatus") val documentExpirationStatus : String,
    @SerializedName("AD") val aD : String,
    @SerializedName("ADM") val aDM : String,
    @SerializedName("LoginClient") val loginClient : Int,
    @SerializedName("ResponseCode") val responseCode : Int,
    @SerializedName("Description") val description : String,
    @SerializedName("ResponseObject") val responseObject : String
)
