package com.makertech.collegeapp.data.remote

import com.makertech.collegeapp.data.remote.request.AttendancePerSubjectRequest
import com.makertech.collegeapp.data.remote.request.LoginRequest
import com.makertech.collegeapp.data.remote.request.TimetableRequest
import com.makertech.collegeapp.data.remote.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CollegeApi {

    @POST("/studentLogin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ):Response<SimpleResponse>

    @GET("/getStudentByEmail")
    suspend fun getStudentByEmail():Response<Student>

    @GET("/getAllPosts")
    suspend fun getAllPosts():Response<List<Post>>

    @POST("/attendance/get/student")
    suspend fun getAttendance(
        @Body attendancePerSubjectRequest: AttendancePerSubjectRequest
    ):Response<AttendanceResponse>

    @POST("/getTimeTable")
    suspend fun getTimetable(
        @Body timetableRequest: TimetableRequest
    ):Response<List<TimeTableResponse>>

}