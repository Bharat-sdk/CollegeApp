package com.makertech.tnuteacherapp.ui.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.request.GiveAttendanceRequest
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.data.remote.response.SimpleResponse
import com.makertech.tnuteacherapp.other.Resource
import com.makertech.tnuteacherapp.repository.StudentRepository
import com.markertech.data.request.StudentPerSemAndDept
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakeAttendanceViewModel @Inject constructor(
    private val studentRepository: StudentRepository
): ViewModel() {
    private val _getAttendanceStatus = MutableLiveData<Resource<List<AttendancePerStudent>>>()
    val getAttendanceStatus : LiveData<Resource<List<AttendancePerStudent>>> = _getAttendanceStatus

    private val _giveAttendanceStatus = MutableLiveData<Resource<SimpleResponse>>()
    val giveAttendanceStatus : LiveData<Resource<SimpleResponse>> = _giveAttendanceStatus

    fun getAttendanceList(studentPerSemAndDept: StudentPerSemAndDept) {
        _getAttendanceStatus.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result =  studentRepository.getStudentPerSemDept(studentPerSemAndDept)
            _getAttendanceStatus.postValue(result)
        }
    }

    fun giveAttendance(subject:String,attendance:List<AttendancePerStudent>)
    {
        _giveAttendanceStatus.postValue(Resource.loading(null))
        if (subject.isEmpty())
        {
            _giveAttendanceStatus.postValue(Resource.error("Please Select The Subject",null))
        }
        else
        {
            viewModelScope.launch {
              val result =  studentRepository.giveAttendance(GiveAttendanceRequest(subject,attendance))
                _giveAttendanceStatus.postValue(result)
            }

        }
    }
}