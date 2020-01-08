package com.koktajlbar.incognitobook.view

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView

import com.koktajlbar.incognitobook.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sv = findViewById<SearchView>(R.id.search)
        sv.setMaxWidth(Integer.MAX_VALUE);
        val queryHint = resources.getString(R.string.search_hint)
        sv.setQueryHint(queryHint)
        supportFragmentManager.beginTransaction().add(R.id.container, CocktailsFragment()).commit()
        tabLayout.addOnTabSelectedListener(t)
    }

    internal var t: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            var transaction = supportFragmentManager.beginTransaction()
            when (tab.position) {
                0 -> transaction.replace(R.id.container, CocktailsFragment()).commit()
                1 ->
                {
                    var x = CocktailsFragment()
                    x.signature = true
                    transaction.replace(R.id.container, x).commit()
                }
                2 -> transaction.replace(R.id.container, GuestsFragment()).commit()
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
