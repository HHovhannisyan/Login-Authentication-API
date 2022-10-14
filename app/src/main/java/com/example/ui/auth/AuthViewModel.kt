package com.example.ui.auth

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.data.network.EncyptedData
import com.example.data.network.Resource
import com.example.data.repository.AuthRepository
import com.example.data.responses.LoginResponse
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse


    fun login(
        clientIdentifier: String,
        partnerId: String,
        password: String,
        encyptedStr: String
    ) {
        viewModelScope.launch {
            val encryptedData = EncyptedData(clientIdentifier, password, encyptedStr)
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(partnerId, encryptedData)
        }
    }


    fun getPartnerId(context: Context):Int{
        return repository.getPartnerId(context)
    }

    fun getPartnerSite(context: Context):String{
        return repository.getPartnerSite(context)
    }
}
