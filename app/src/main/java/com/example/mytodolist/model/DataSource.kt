package com.example.mytodolist.model

data class DataSource (
    var id: Int = 0,
    var title: String,
    var desc: String,
    var hour: String,
    var date: String

){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataSource

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}