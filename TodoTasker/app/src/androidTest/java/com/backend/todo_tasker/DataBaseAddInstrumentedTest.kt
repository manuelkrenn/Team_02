package com.backend.todo_tasker

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backend.todo_tasker.database.DatabaseClass
import com.backend.todo_tasker.database.Todo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataBaseAddInstrumentedTest {
    @Test
    fun singleAddPass() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = DatabaseClass(appContext)
        val datab = db.createDb()
        db.deleteDBEntries(datab)

        val timeInMillis = db.dateToMillis(db.getCurrentDate())
        val testTodo = Todo(6, "Test", timeInMillis, null)

        val retVal = db.addToDb(datab, testTodo)
        assertNotEquals(retVal, -1)
    }

    @Test
    fun multiAddPass() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = DatabaseClass(appContext)
        val datab = db.createDb()
        db.deleteDBEntries(datab)

        val timeInMillis = db.dateToMillis(db.getCurrentDate())
        for(i in 0..50) {
            val testTodo = Todo(i, "Test", timeInMillis, null)
            val retVal = db.addToDb(datab, testTodo)
            assertNotEquals(retVal, -1)
        }
    }

    @Test
    fun multiUIDFail() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = DatabaseClass(appContext)
        val datab = db.createDb()
        db.deleteDBEntries(datab)

        val timeInMillis = db.dateToMillis(db.getCurrentDate())
        var testTodo = Todo(1337, "TestThatPasses", timeInMillis, null)
        var retVal = db.addToDb(datab, testTodo)
        assertNotEquals(retVal, -1)

        for(i in 0..50) {
            testTodo = Todo(1337, "TestThatFails", timeInMillis, null)
            retVal = db.addToDb(datab, testTodo)
            assertEquals(retVal, -1)
        }
    }
}