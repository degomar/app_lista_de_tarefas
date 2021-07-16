package com.example.mytodolist.DataSource

import com.example.mytodolist.model.DataSource

object TaskListDataSource{
     val list = arrayListOf<DataSource>()

    
    fun getList() = list.toList()

    fun insertTask(data: DataSource){
        if (data.id == 0) {
        list.add(data.copy(id = list.size + 1))
        } else {
            list.remove(data)
            list.add(data)
        }
    }

    fun findById(taskID: Int) = list.find {it.id == taskID }
    fun deleteTask(it: DataSource) {
        list.remove(it)
    }

}