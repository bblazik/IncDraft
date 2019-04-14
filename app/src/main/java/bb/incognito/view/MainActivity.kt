package bb.incognito.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

import bb.incognito.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, CocktailsFragment()).commit()
        tabLayout.addOnTabSelectedListener(t)
    }

    internal var t: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            var transaction = supportFragmentManager.beginTransaction()
            when (tab.position) {
                0 -> transaction.replace(R.id.container, CocktailsFragment()).commit()
                1 -> transaction.replace(R.id.container, GuestsFragment()).commit()
                2 ->
                {
                    var x = CocktailsFragment()
                    x.menu = true
                    transaction.replace(R.id.container, x).commit()
                }

            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    }
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true) // ??
        }
    }
}
