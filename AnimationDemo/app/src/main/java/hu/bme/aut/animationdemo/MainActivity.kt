package hu.bme.aut.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import hu.bme.aut.animationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val demoAnim = AnimationUtils.loadAnimation(this,
            R.anim.demo_anim)


        demoAnim.setAnimationListener(
            object: AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    // start a new activity here..
                    Toast.makeText(
                        this@MainActivity,"ANIM END",
                        Toast.LENGTH_LONG).show()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }
            }
        )


        binding.btnAnim.setOnClickListener {
            binding.btnAnim.startAnimation(demoAnim)
        }
    }
}