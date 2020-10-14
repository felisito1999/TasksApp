package com.example.tasksapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_update_task.*

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var taskViewModel : TaskViewModel
    private lateinit var task : Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        var intent = intent
        task = intent.getParcelableExtra<Task>("taskObject") as Task
        updateTaskEditText.setText(task.Description)

        if(task.Completed){
            completionButton.setText(R.string.mark_restarted)
        }
        else {
            completionButton.setText(R.string.mark_completed)
        }

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        updateButton.setOnClickListener {
            val description = updateTaskEditText.text.toString()
            val updatedTask = Task(task.Id, description, task.Completed)
            updateTask(updatedTask)
        }

        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Yes") { _ , _ ->
                taskViewModel.delete(task)
                Toast.makeText(this, "The task has been deleted successfully", Toast.LENGTH_LONG).show()
                this.finish()
            }
            builder.setNegativeButton("No") {_ ,_ ->

            }

            builder.setTitle("Deleting dialog")
            builder.setMessage("Are you sure deleting this task?")
            builder.create().show()
        }

        completionButton.setOnClickListener {
            var alteredTask = Task(task.Id, task.Description, task.Completed)

            if(task.Completed){
                alteredTask.Completed = false
                updateTask(alteredTask)
            }
            else{
                alteredTask.Completed = true
                updateTask(alteredTask)
            }
        }
    }

    private fun updateTask(updatedTask : Task) {

        if (updateTaskEditText.text.isEmpty() || updateTaskEditText.text.isBlank()){
            Toast.makeText(this, "The task should be properly filled!", Toast.LENGTH_LONG).show()
        }
        else {
            taskViewModel.update(updatedTask)
            Toast.makeText(this, "The task has been updated", Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }

}