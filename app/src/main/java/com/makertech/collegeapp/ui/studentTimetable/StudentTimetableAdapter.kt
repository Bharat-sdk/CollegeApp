package com.makertech.collegeapp.ui.studentTimetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makertech.collegeapp.data.remote.response.TimeTableResponse
import com.makertech.collegeapp.databinding.ItemDailyRoutineBinding

class StudentTimetableAdapter (var timetableList: ArrayList<TimeTableResponse>):
    RecyclerView.Adapter<StudentTimetableAdapter.TimetableViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentTimetableAdapter.TimetableViewHolder {
        val binding = ItemDailyRoutineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentTimetableAdapter.TimetableViewHolder, position: Int) {
        with(holder){
            with(timetableList[position]){
                binding.txtCapitalLetterSubject.text = this.subject[0].toString()
                binding.txtSubject.text = this.subject
                binding.txtPeriod.text = this.period.toString()

            }
        }
    }

    override fun getItemCount(): Int {
        return timetableList.size
    }


    inner class TimetableViewHolder(val binding: ItemDailyRoutineBinding): RecyclerView.ViewHolder(binding.root)
}