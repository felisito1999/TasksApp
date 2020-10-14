package com.example.tasksapp

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao : TasksDao) {

    val allTasks : LiveData<List<Task>> = taskDao.getAllTasks()

    val completedTasks : LiveData<List<Task>> = taskDao.getCompletedTasks()

    val uncompletedTasks : LiveData<List<Task>> = taskDao.getUncompletedTasks()

    suspend fun insert(task : Task) {
        taskDao.insert(task)
    }

    suspend fun update(task : Task) {
        taskDao.update(task);
    }

    suspend fun delete(task : Task ) {
        taskDao.delete(task)
    }
}