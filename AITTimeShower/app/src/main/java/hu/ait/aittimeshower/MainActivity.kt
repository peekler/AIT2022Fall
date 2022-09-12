package hu.ait.aittimeshower

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import hu.ait.aittimeshower.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var myCar = Car("Toyota",100)
        myCar.getName()



        binding.btnTime.setOnClickListener {
            revealCard()
            Log.d("TAG_MAIN", "time button was clicked")
            val timeText = getString(R.string.text_date,
                Date(System.currentTimeMillis()).toString())

            binding.tvTime.text = timeText

            //Toast.makeText(this,
            //    "${binding.etName.text.toString()} $timeText",
            //    Toast.LENGTH_LONG).show()

            Snackbar.make(binding.root,
                "${binding.etName.text.toString()} $timeText",
                Snackbar.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun revealCard() {
        val x = binding.cardView.getRight()
        val y = binding.cardView.getBottom()

        val startRadius = 0
        val endRadius = Math.hypot(binding.cardView.getWidth().toDouble(),
            binding.cardView.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            binding.cardView,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        anim.duration = 5000

        binding.cardView.setVisibility(View.VISIBLE)
        anim.start()
    }


}