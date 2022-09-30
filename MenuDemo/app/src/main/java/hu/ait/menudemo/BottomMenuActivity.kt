package hu.ait.menudemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.ait.menudemo.databinding.ActivityBottomMenuBinding

class BottomMenuActivity : AppCompatActivity() {

    lateinit var binding: ActivityBottomMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.setOnNavigationItemSelectedListener(
            myOnNavigationItemSelectedListener)
    }

    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                Toast.makeText(this,"Home",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_settings -> {
                Toast.makeText(this,"Settings",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}