package hu.bme.aut.viewpagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.viewpagerdemo.pageanim.DepthPageTransformer
import hu.bme.aut.viewpagerdemo.pageanim.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(this@MainActivity, "Selected position: ${position}",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewPagerAdapter = MyViewPagerAdapter(this, 2)
        mainViewPager.adapter = myViewPagerAdapter

        mainViewPager.registerOnPageChangeCallback(pageChangeCallback)

        var pageNames: Array<String> = resources.getStringArray(R.array.tab_names)
        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->
            tab.text = pageNames[position]
        }.attach()

        //mainViewPager.setPageTransformer(ZoomOutPageTransformer())
        mainViewPager.setPageTransformer(DepthPageTransformer())
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewPager.unregisterOnPageChangeCallback(pageChangeCallback)
    }
}