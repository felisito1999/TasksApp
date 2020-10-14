package com.example.tasksapp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.icu.text.UnicodeSet.EMPTY
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class TaskListAdapter internal constructor(
    context : Context
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()

    inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val taskItemView: TextView = itemView.findViewById(R.id.taskTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = tasks[position]
        holder.taskItemView.text = current.Description

        holder.taskItemView.setOnClickListener {
            val context = holder.taskItemView.context
            val intent = Intent(context, UpdateTaskActivity::class.java)
            intent.putExtra("taskObject", current)
            startActivity(context, intent, Bundle.EMPTY)
        }

        if(current.Completed) {
            holder.taskItemView.taskTextView.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
        else {
            holder.itemView.taskTextView.apply {
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    internal fun setTasks(tasks : List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun getItemCount() = tasks.size
}