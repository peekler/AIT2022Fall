package hu.ait.roomgradesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.roomgradesdemo.data.AppDatabase
import hu.ait.roomgradesdemo.data.Grade
import hu.ait.roomgradesdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            insertGrade()
        }

        binding.btnQuery.setOnClickListener {
            queryGrades()
        }
    }

    fun insertGrade() {
        Thread {
            val newGrade = Grade(
                null,
                binding.etStudentId.text.toString(),
                binding.etgrade.text.toString()
            )
            AppDatabase.getInstance(this).gradeDao().insertGrade(newGrade)
        }.start()
    }

    fun queryGrades() {
        Thread {
            val grades = AppDatabase.getInstance(this).gradeDao().getAllGrades()
            runOnUiThread{
                binding.tvResult.text = ""
                grades.forEach {
                    binding.tvResult.append("${it.studentId} ${it.grade}\n")
                }
            }
        }.start()
    }


}