package com.example.tasksapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {
    @Query("SELECT * from tasks_table ORDER BY Id DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * from tasks_table WHERE completed = 1 ORDER BY Id DESC")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * from tasks_table WHERE completed = 0 ORDER BY Id DESC")
    fun getUncompletedTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}