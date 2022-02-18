package com.makertech.collegeapp.data.remote.request

data class TimetableRequest(
    val dayOfWeek: String,
    val department:String,
    val semester:String
)
