package hu.ait.todorecyclerviewdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO : Data Access Object
@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo")
    fun getAllTodo() : LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE todotitle LIKE '%' || :text || '%'")
    fun findTodos(text: String) : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo")
    fun deleteAllTodos()

    @Update
    fun updateTodo(todo: Todo)
}