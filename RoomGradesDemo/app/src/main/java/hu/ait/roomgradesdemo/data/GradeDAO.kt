package hu.ait.roomgradesdemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GradeDAO {

    @Query("SELECT * FROM grades")
    fun getAllGrades() : List<Grade>

    @Insert
    fun insertGrade(grade: Grade)

    @Query("DELETE FROM grades")
    fun deleteAllGrades()
}