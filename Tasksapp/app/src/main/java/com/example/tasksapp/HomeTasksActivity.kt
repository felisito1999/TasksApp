package com.example.tasksapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeTasksActivity : AppCompatActivity() {
    private lateinit var taskViewModel : TaskViewModel
    private val addTaskActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tasks)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TaskListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.allTasks.observe(this, Observer { tasks ->
            tasks?.let { adapter.setTasks(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@HomeTasksActivity, AddTask::class.java)
            startActivityForResult(intent, addTaskActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddTask.EXTRA_REPLY)?.let {
                val task = Task(0,it, false)
                taskViewModel.insert(task)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.filtering_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val adapter = TaskListAdapter(this)
        when(item.itemId){
            R.id.completedTasksOption -> {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TaskListAdapter(this)

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

                taskViewModel.completedTasks.observe(this, Observer { tasks ->
                    tasks?.let { adapter.setTasks(it) }
                })
            }
            R.id.ongoingTasksOption -> {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TaskListAdapter(this)

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

                taskViewModel.uncompletedTasks.observe(this, Observer { tasks ->
                    tasks?.let { adapter.setTasks(it) }
                })
            }
            R.id.allTasksOption -> {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TaskListAdapter(this)

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

                taskViewModel.allTasks.observe(this, Observer { tasks ->
                    tasks?.let { adapter.setTasks(it) }
                })
            }
        }
/*        val adapter = TaskListAdapter(this)
        if(item.itemId == R.id.completedTasksOption){
            taskViewModel.allTasks.observe(this, Observer { task ->
                val tasks = task.filter{task -> task.Completed}
                adapter.setTasks(task)
            })
        }*/
        return super.onOptionsItemSelected(item)
    }
}