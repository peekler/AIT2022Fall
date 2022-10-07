package hu.ait.viewdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import hu.ait.viewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val cityNames = arrayOf(
        "Budapest", "Bukarest", "New York", "New Delhi")

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.citinames)
        )
        binding.autoCities.threshold = 1
        binding.autoCities.setAdapter(cityAdapter)

        binding.btnRed.setOnClickListener {
            binding.root.setBackgroundColor(Color.RED)
        }
        binding.btnBlue.setOnClickListener {
            binding.root.setBackgroundColor(Color.BLUE)
        }
        binding.btnWhite.setOnClickListener {
            binding.root.setBackgroundColor(Color.WHITE)
        }

        //----
        val fruitsAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.fruits, android.R.layout.simple_spinner_item
        )
        fruitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFruits.adapter = fruitsAdapter


    }
}