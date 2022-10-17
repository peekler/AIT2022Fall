package hu.ait.roomgradesdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grades")
data class Grade(
    @PrimaryKey(autoGenerate = true) var gradeID: Long?,
    @ColumnInfo(name = "studentid") var studentId: String,
    @ColumnInfo(name = "grade") var grade: String,
)