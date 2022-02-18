package com.makertech.collegeapp.ui.studentAttendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.collegeapp.data.remote.request.AttendancePerSubjectRequest
import com.makertech.collegeapp.data.remote.response.AttendanceResponse
import com.makertech.collegeapp.other.Resource
import com.makertech.collegeapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentAttendanceViewModel @Inject constructor(
    val studentRepository: StudentRepository
): ViewModel() {
    private val _attendance = MutableLiveData<Resource<AttendanceResponse>>()
    val attendance : LiveData<Resource<AttendanceResponse>> = _attendance

    fun getAttendanceOfStudent(attendancePerSubjectRequest: AttendancePerSubjectRequest)
    {
        _attendance.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.getAttendance(attendancePerSubjectRequest )
            _attendance.postValue(result)
        }
    }
}