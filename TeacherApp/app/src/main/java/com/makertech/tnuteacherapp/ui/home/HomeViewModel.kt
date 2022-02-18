package com.makertech.tnuteacherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.request.GiveAttendanceRequest
import com.makertech.tnuteacherapp.data.remote.response.Teacher
import com.makertech.tnuteacherapp.other.Resource
import com.makertech.tnuteacherapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
private val studentRepository: StudentRepository
): ViewModel() {
    private val _teacherDetails = MutableLiveData<Resource<Teacher>>()
    val teacherDetails : LiveData<Resource<Teacher>> = _teacherDetails



    fun getTeacherDetails()
    {
        _teacherDetails.postValue(Resource.loading(null))
            viewModelScope.launch {
                val result =  studentRepository.getTeacherDetails()
                _teacherDetails.postValue(result)

        }
    }
}