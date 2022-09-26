package hu.ait.multiactivitydemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import hu.ait.multiactivitydemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_ORDER = "KEY_ORDER"
        const val KEY_RESULT = "KEY_RESULT"
    }

    lateinit var binding: ActivityMainBinding

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultIntent = result.data

            binding.tvResult.text = resultIntent?.getStringExtra(
                KEY_RESULT)

        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            binding.tvResult.text = "Cancelled"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDetails.setOnClickListener {
            val intentDetails = Intent()
            intentDetails.setClass(
                this, DetailsActivity::class.java
            )
            intentDetails.putExtra(
                KEY_ORDER, binding.etData.text.toString()
            )

            //startActivity(intentDetails)
            DataManager.myData =
                mutableListOf<String>("333","33")

            startForResult.launch(intentDetails)
        }
    }
}