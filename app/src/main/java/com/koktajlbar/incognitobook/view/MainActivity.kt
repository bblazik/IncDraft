package com.koktajlbar.incognitobook.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

import com.koktajlbar.incognitobook.R

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
