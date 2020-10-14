package com.example.tasksapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Task::class], version = 1, exportSchema = false)
public abstract class TasksDatabase : RoomDatabase() {

    abstract fun tasksDao() : TasksDao

    companion object {
        @Volatile
        var INSTANCE : TasksDatabase? = null

        fun getDatabase(context : Context) : TasksDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "tasks_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

private class TasksDatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        TasksDatabase.INSTANCE?.let { database ->
            scope.launch {
                populateDatabase(database.tasksDao())
            }
        }
    }

    suspend fun populateDatabase(taskDao: TasksDao) {

        var task = Task(1, "Finish the projects", true)
        taskDao.insert(task)
    }
}