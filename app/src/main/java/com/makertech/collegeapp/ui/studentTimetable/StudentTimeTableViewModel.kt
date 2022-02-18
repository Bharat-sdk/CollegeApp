package com.makertech.collegeapp.ui.studentTimetable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makertech.collegeapp.data.remote.request.TimetableRequest
import com.makertech.collegeapp.data.remote.response.TimeTableResponse
import com.makertech.collegeapp.other.Resource
import com.makertech.collegeapp.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentTimeTableViewModel @Inject constructor(
    private val studentRepository: StudentRepository
): ViewModel() {
    private val _timetable = MutableLiveData<Resource<List<TimeTableResponse>>>()
    val timetable : LiveData<Resource<List<TimeTableResponse>>> = _timetable

    fun getTimetable(dayOfWeek:String,department:String,semester:String)
    {
        _timetable.postValue(Resource.loading(null))
        if (dayOfWeek.isEmpty() || department.isEmpty() || semester.isEmpty())
        {
           _timetable.postValue(Resource.error("Please Fill All the Fields",null))

        }
        viewModelScope.launch {
            val result =  studentRepository.getTimeTable(TimetableRequest(dayOfWeek,department, semester))
            _timetable.postValue(result)
        }
    }

}