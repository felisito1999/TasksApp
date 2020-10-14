package com.example.tasksapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : TaskRepository

    val allTasks : LiveData<List<Task>>
    val completedTasks : LiveData<List<Task>>
    val uncompletedTasks : LiveData<List<Task>>

    init {
        val tasksDao = TasksDatabase.getDatabase(application).tasksDao()
        repository = TaskRepository(tasksDao)
        allTasks = repository.allTasks
        completedTasks = repository.completedTasks
        uncompletedTasks = repository.uncompletedTasks
    }

    fun insert(task : Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun update(task : Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun delete(task : Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }
}