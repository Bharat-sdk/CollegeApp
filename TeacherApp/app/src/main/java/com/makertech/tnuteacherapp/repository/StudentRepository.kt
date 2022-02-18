package com.makertech.tnuteacherapp.repository

import android.app.Application
import android.net.Uri
import com.makertech.tnuteacherapp.data.remote.CollegeApi
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.request.GiveAttendanceRequest
import com.makertech.tnuteacherapp.data.remote.request.LoginRequest
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.other.Resource
import com.markertech.data.request.StudentPerSemAndDept
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.create
import java.io.File
import javax.inject.Inject


class StudentRepository @Inject constructor(
    private val collegeApi: CollegeApi,
    private val context: Application,
){
    suspend fun login(email:String,password:String)= withContext(Dispatchers.IO)
    {
       try {
            val response = collegeApi.login(LoginRequest(email,password))
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
            val response = collegeApi.getAllPosts()
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
            Resource.error("Could not connect to server Check your Internet Connection ",null)
        }
    }

    suspend fun insertPost(post: Post) = withContext(Dispatchers.IO)
    {
        try {
            val response = collegeApi.insertPost(post)
            if (response.isSuccessful)
            {
                Resource.success(response.body()?.message)
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

    suspend fun getStudentPerSemDept(studentPerSemAndDept: StudentPerSemAndDept)= withContext(Dispatchers.IO)
    {
        try {
            val response = collegeApi.getStudentPerSemDept(studentPerSemAndDept)
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
            Resource.error("Could not connect to server Check your Internet Connection " + e.message,null)
        }
    }

    suspend fun giveAttendance(attendance:GiveAttendanceRequest)= withContext(Dispatchers.IO)
    {
        try {
            val response = collegeApi.giveAttendance(attendance)
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

    suspend fun uploadImage(imageUri: Uri) = withContext(Dispatchers.IO)
    {
        try {
            val file = File(imageUri.path)
            val requestFile = create(
                context.contentResolver.getType(imageUri)?.toMediaTypeOrNull(),
                file
            )
            val body = MultipartBody.Part.createFormData("",file.name,requestFile)

            val response = collegeApi.uploadImage(body)
            if (response.isSuccessful)
            {
                Resource.success(response.body()?.message)
            }
            else
            {

                Resource.error("Error Occurred ",null)
            }
        }catch (e:Exception)
        {
            Resource.error("Could not connect to server Check your Internet Connection ",e.message)
        }
    }

    suspend fun getTeacherDetails()= withContext(Dispatchers.IO)
    {
        try {
            val response = collegeApi.getTeacherByEmail()
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