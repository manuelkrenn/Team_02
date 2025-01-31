package com.backend.todo_tasker.database

import android.content.Context
import androidx.room.Room
import java.util.*

class DatabaseClass(context: Context) {
    private var appContext = context

    fun createDb(): TodoDatabase {
        return Room.databaseBuilder(
                appContext,
                TodoDatabase::class.java, "todo-database"
        ).build()
    }

    fun addToDb(db: TodoDatabase, test_todo: Todo): Int {
        val todos: List<Todo> = getAllDb(db)

        val uids: MutableList<Int> = arrayListOf()
        for (todo in todos) {
            uids.add(todo.uid)
        }

        if (uids.contains(test_todo.uid)) {
            println("UID already in DB")
            return -1
        }

        db.todoDao().insert(test_todo)
        println("Added TODO to DB")
        return 0
    }

    fun getAllDb(db: TodoDatabase): List<Todo> {
        println("Getting all DB entries")
        return db.todoDao().getAll()
    }

    fun deleteDBEntries(db: TodoDatabase) {
        val todoDao = db.todoDao()
        todoDao.deleteAll()
    }

    fun getLastEntry(db: TodoDatabase): Todo {
        return db.todoDao().getLastEntry()
    }

    fun dateToMillis(date: Date): Long {
        return date.time
    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }

    fun getNextDate(db: TodoDatabase): Todo {
        return db.todoDao().getNextDate();
    }
    fun deleteDBSingleEntry(db: TodoDatabase, uid: Int) {
        db.todoDao().deleteSingle(uid)
    }

    fun duplicateDBEntry(db: TodoDatabase, uid: Int):Int {
        var toDuplicate:Todo= getSingleEntry(db,uid)
        val lastEntry: Todo = getLastEntry(db)
        var nextId = lastEntry.uid+1
        val toInsert : Todo = Todo(nextId,toDuplicate.title,toDuplicate.date,toDuplicate.reminder)
        addToDb(db,toInsert)
        return nextId
    }

    fun updateEntry(db:TodoDatabase, uid:Int, title:String, date:Long, reminder:Long){
        db.todoDao().update(uid, title, date ,reminder)
    }

    fun getSingleEntry(db: TodoDatabase, uid:Int): Todo {
        return db.todoDao().getSingle(uid)
    }
}