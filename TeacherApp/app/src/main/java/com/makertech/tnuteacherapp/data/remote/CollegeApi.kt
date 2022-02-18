package com.makertech.tnuteacherapp.data.remote

import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.request.GiveAttendanceRequest
import com.makertech.tnuteacherapp.data.remote.request.LoginRequest
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.data.remote.response.SimpleResponse
import com.makertech.tnuteacherapp.data.remote.response.Teacher
import com.markertech.data.request.StudentPerSemAndDept
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CollegeApi {

    @POST("/teacherLogin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ):Response<SimpleResponse>


    @GET("/getAllPosts")
    suspend fun getAllPosts():Response<List<Post>>

    @Multipart
    @POST("/uploadPostImage")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ):Response<SimpleResponse>

    @POST("/createPost")
    suspend fun insertPost(
        @Body post: Post
    ):Response<SimpleResponse>

    @POST("/attendance/give")
    suspend fun giveAttendance(
        @Body attendance:GiveAttendanceRequest
    ):Response<SimpleResponse>

    @POST("/getAllStudents/sem/dept")
    suspend fun getStudentPerSemDept(
        @Body studentPerSemAndDept: StudentPerSemAndDept
    ):Response<List<AttendancePerStudent>>

    @GET("/teacher/getDetails")
    suspend fun getTeacherByEmail():Response<Teacher>
}