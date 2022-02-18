package com.makertech.tnuteacherapp.ui.attendance

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.response.Student
import com.makertech.tnuteacherapp.databinding.ItemTakeAttendanceBinding
import com.markertech.data.request.StudentPerSemAndDept

class TeacherAttendanceAdapter(val studentList: ArrayList<AttendancePerStudent>):
    RecyclerView.Adapter<TeacherAttendanceAdapter.TeacherAttendanceViewHolder>() {

    var attendanceList :ArrayList<AttendancePerStudent> = studentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherAttendanceViewHolder {
        val binding = ItemTakeAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherAttendanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherAttendanceViewHolder, position: Int) {
        with(holder){
            with(attendanceList[position]){
                binding.itemTxtStudentUID.text = this.id
                if (this.presentOrAbsent)
                {
                    binding.itemCheckPresentAbsent.isChecked = true
                }

                binding.itemCheckPresentAbsent.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked)
                    {
                        attendanceList[position].presentOrAbsent = true
                    }
                }
            }

        }
    }

    fun getAttendance():ArrayList<AttendancePerStudent>{
        return attendanceList
    }
    fun emptyData(){
        attendanceList.clear()
    }

    override fun getItemCount(): Int {
        return studentList.size
    }



    inner class TeacherAttendanceViewHolder(val binding: ItemTakeAttendanceBinding): RecyclerView.ViewHolder(binding.root)



}