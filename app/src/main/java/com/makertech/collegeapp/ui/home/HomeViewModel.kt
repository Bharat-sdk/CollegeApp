package com.makertech.collegeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.collegeapp.data.remote.response.Student
import com.makertech.collegeapp.other.Resource
import com.makertech.collegeapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val studentRepository: StudentRepository
): ViewModel() {
    private val _studentDetails = MutableLiveData<Resource<Student>>()
    val studentDetails : LiveData<Resource<Student>> = _studentDetails



    fun getStudentDetails()
    {
        _studentDetails.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.getStudentDetails()
            _studentDetails.postValue(result)

        }
    }
}