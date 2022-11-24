package hu.bme.aut.simplefragmentdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import androidx.fragment.app.Fragment
import hu.bme.aut.simplefragmentdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMain.setOnClickListener {
            showFragementByTag(InputFragment.TAG)
        }
        binding.btnDetails.setOnClickListener {
            showFragementByTag(DetailsFragment.TAG)
        }

        showFragementByTag(InputFragment.TAG)
    }

    fun showFragementByTag(tag: String) {
        var fragment : Fragment? =
            supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (tag == InputFragment.TAG) {
                fragment = InputFragment()
            } else {
                fragment = DetailsFragment()
            }
        }

        val fragTrans = supportFragmentManager.beginTransaction()
        fragTrans.replace(R.id.layoutContainer,fragment)
        fragTrans.addToBackStack(null)
        fragTrans.commit()
    }
}