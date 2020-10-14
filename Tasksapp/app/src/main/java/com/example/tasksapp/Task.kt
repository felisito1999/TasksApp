package com.example.tasksapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Entity(tableName = "tasks_table")
@Parcelize
data class Task (
    @PrimaryKey(autoGenerate = true)
    val Id : Int,
    @ColumnInfo(name = "description")
    val Description : String,
    @ColumnInfo(name = "completed")
    var Completed : Boolean
) : Parcelable