package hu.ait.todorecyclerviewdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) var _todoId: Long?,
    @ColumnInfo(name = "todotitle") var todoTitle: String,
    @ColumnInfo(name = "createdate") var createDate: String,
    @ColumnInfo(name = "isdone") var isDone: Boolean) : java.io.Serializable {
}

