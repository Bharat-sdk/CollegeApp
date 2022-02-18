package com.makertech.tnuteacherapp.data.remote.request


data class AttendancePerStudent(
    val id:String,
    var presentOrAbsent:Boolean
){
    companion object{
        operator fun invoke(
            id:String?=null,
            presentOrAbsent: Boolean?=null)
        = AttendancePerStudent(
            id?:"",
            presentOrAbsent?:false
        )
    }
}
