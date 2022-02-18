package com.makertech.collegeapp.repository

import android.app.Application
import com.makertech.collegeapp.data.remote.CollegeApi
import com.makertech.collegeapp.data.remote.request.AttendancePerSubjectRequest
import com.makertech.collegeapp.data.remote.request.LoginRequest
import com.makertech.collegeapp.data.remote.request.TimetableRequest
import com.makertech.collegeapp.other.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val studentCollegeApi: CollegeApi,
    private val context: Application
){
    suspend fun login(email:String,password:String)= withContext(Dispatchers.IO)
    {
       try {
            val response = studentCollegeApi.login(LoginRequest(email,password))
            if (response.isSuccessful && response.body()!!.successful) {
              Resource.success(response.body()?.message)
            }
            else{
                Resource.error(response.body()?.message ?: response.message() ,null)
            }
        }
        catch(e:Exception)
        {
            Resource.error("Could not connect to server Check your Internet Connection ",null)
        }
    }

    suspend fun getAllPosts() = withContext(Dispatchers.IO)
    {
        try {
            val response = studentCollegeApi.getAllPosts()
            if (response.isSuccessful)
            {
                Resource.success(response.body())
            }
            else
            {
                Resource.error("Error Occured ",null)
            }
        }catch (e:Exception)
        {
            Resource.error("Could not connect to server Check your Internet Connection ",null)
        }
    }

    suspend fun getAttendance(attendancePerSubjectRequest: AttendancePerSubjectRequest) = withContext(Dispatchers.IO)
    {
        try {
            val response = studentCollegeApi.getAttendance(attendancePerSubjectRequest)
            if (response.isSuccessful)
            {
                Resource.success(response.body())
            }
            else
            {
                Resource.error("Error Occured ",null)
            }
        }catch (e:Exception)
        {
            Resource.error("Could not connect to server Check your Internet Connection " + e.localizedMessage,null)
        }
    }

    suspend fun getStudentDetails()= withContext(Dispatchers.IO)
    {
        try {
            val response = studentCollegeApi.getStudentByEmail()
            if (response.isSuccessful)
            {
                Resource.success(response.body())
            }
            else
            {
                Resource.error("Error Occurred ",null)
            }
        }catch (e:Exception)
        {
            Resource.error(e.localizedMessage,null)
        }
    }

    suspend fun getTimeTable(timetableRequest: TimetableRequest)= withContext(Dispatchers.IO)
    {
        try {
            val response = studentCollegeApi.getTimetable(timetableRequest)
            if (response.isSuccessful)
            {
                Resource.success(response.body())
            }
            else
            {
                Resource.error("Error Occurred ",null)
            }
        }catch (e:Exception)
        {
            Resource.error(e.localizedMessage,null)
        }
    }
}