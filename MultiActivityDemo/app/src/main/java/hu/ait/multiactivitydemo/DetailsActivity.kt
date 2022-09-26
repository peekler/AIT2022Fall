package hu.ait.multiactivitydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.multiactivitydemo.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(MainActivity.KEY_ORDER))
        {
            binding.tvData.text =
                intent.getStringExtra(MainActivity.KEY_ORDER)
        }

        //DataManager.myData..


        binding.btnOk.setOnClickListener {
            val intentResult = Intent()
            intentResult.putExtra(MainActivity.KEY_RESULT,
                "Order accepted")
            setResult(RESULT_OK, intentResult)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            val intentResult = Intent()
            setResult(RESULT_CANCELED, intentResult)
            finish()
        }
    }
}