package com.makertech.tnuteacherapp.data.remote.request


data class GiveAttendanceRequest(
    val subject:String,
    val attendanceList:List<AttendancePerStudent>
)
