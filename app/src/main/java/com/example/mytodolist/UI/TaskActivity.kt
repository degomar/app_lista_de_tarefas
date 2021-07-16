package com.example.mytodolist.UI

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mytodolist.DataSource.TaskListDataSource
import com.example.mytodolist.Extensions.format
import com.example.mytodolist.Extensions.text
import com.example.mytodolist.R
import com.example.mytodolist.databinding.ActivityTaskBinding
import com.example.mytodolist.model.DataSource
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class TaskActivity : AppCompatActivity() {

    lateinit var layout_binding : ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout_binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(layout_binding.root)

        if (intent.hasExtra(TASK_ID)){
            layout_binding.btnNewTask.text = "ATUALIZAR"
            val taskID = intent.getIntExtra(TASK_ID, 0)
            TaskListDataSource.findById(taskID)?.let {
                layout_binding.tilTitle.text = it.title
                layout_binding.tilDate.text = it.date
                layout_binding.tilHour.text = it.hour
                layout_binding.tilFildTxt.text = it.desc
            }
        }

        insert_listener()
        cancel_insert_Task()
    }
    private fun insert_listener() {
        layout_binding.tilDate.editText?.setOnClickListener {
            val date_Picker = MaterialDatePicker.Builder.datePicker().build()
            date_Picker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                layout_binding.tilDate.text = (Date(it + offset).format())
            }
            date_Picker.show(supportFragmentManager,"DatePicker")
        }

        layout_binding.tilHour.editText?.setOnClickListener {
            val time_picker = MaterialTimePicker.Builder().setTimeFormat(
                TimeFormat.CLOCK_24H
            ).build()

            time_picker.addOnPositiveButtonClickListener {

                val hora = if (time_picker.hour in 0..9) "0${time_picker.hour}" else "${time_picker.hour}"
                val minuto = if (time_picker.minute in 0..9) "0${time_picker.minute}" else "${time_picker.minute}"
                layout_binding.tilHour.text = "${hora}:${minuto}"
            }
            time_picker.show(supportFragmentManager, "TimePicker")
        }

        layout_binding.btnNewTask.setOnClickListener {
            var dataSource = DataSource (
                title = layout_binding.tilTitle.text,
                desc = layout_binding.tilFildTxt.text,
                date = layout_binding.tilDate.text,
                hour = layout_binding.tilHour.text,
                    )
            TaskListDataSource.insertTask(dataSource)
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
    fun cancel_insert_Task(){
        layout_binding.btnCancel.setOnClickListener {
            finish()
        }

    }

 companion object{
     const val TASK_ID = "taskId"
 }
}
