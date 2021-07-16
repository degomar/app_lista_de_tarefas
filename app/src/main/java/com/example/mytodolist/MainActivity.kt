package com.example.mytodolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mytodolist.DataSource.TaskListDataSource
import com.example.mytodolist.UI.TaskActivity
import com.example.mytodolist.UI.TaskAdapter
import com.example.mytodolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var layoutMainActivity: ActivityMainBinding
    private val adapter by lazy { TaskAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        layoutMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layoutMainActivity.root)


        updateList()
        layoutMainActivity.rvTasks.adapter = adapter
        insertTask()
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }


    private fun insertTask() {
        layoutMainActivity.fabAdd.setOnClickListener {
            startActivityForResult(Intent(this, TaskActivity::class.java), CREATE_NEW_TASK)

            adapter.listenerEdit = {
               val  intentEdit = Intent(this, TaskActivity::class.java)
                intentEdit.putExtra(TaskActivity.TASK_ID, it.id)
                startActivityForResult(intentEdit, CREATE_NEW_TASK)
            }

            adapter.listenerDelete = {
                TaskListDataSource.deleteTask(it)
                updateList()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }
        fun updateList(){
            val list = TaskListDataSource.getList()
            if (list.isEmpty()) layoutMainActivity.emptyLayout.emptyStatePage.visibility = View.VISIBLE
            else layoutMainActivity.emptyLayout.emptyStatePage.visibility = View.GONE
            adapter.submitList(list)
        }

companion object {
    private const val CREATE_NEW_TASK = 100
}
}