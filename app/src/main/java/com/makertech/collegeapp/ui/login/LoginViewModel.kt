package com.makertech.collegeapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.collegeapp.other.Resource
import com.makertech.collegeapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val studentRepository: StudentRepository
):ViewModel() {
    private val _loginStatus = MutableLiveData<Resource<String>>()
    val loginStatus : LiveData<Resource<String>> = _loginStatus

    fun login(email:String,password:String)
    {
        _loginStatus.postValue(Resource.loading(null))
        if (email.isEmpty() || password.isEmpty())
        {
            _loginStatus.postValue(Resource.error("Please Fill All the Fields",null))
            return
        }
        viewModelScope.launch {
           val result =  studentRepository.login(email, password)
            _loginStatus.postValue(result)
        }
    }

}