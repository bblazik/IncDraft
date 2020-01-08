package com.koktajlbar.incognitobook.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.model.GuestWithCocktails

class GuestDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guest_detail)
        supportFragmentManager.beginTransaction().add(R.id.container2, GuestDetailFragment()).commit()
    }
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
        fun launchDetail(context: Context, guestWithCocktails: GuestWithCocktails): Intent {
            val intent = Intent(context, GuestDetail::class.java)
            intent.putExtra("GUEST", guestWithCocktails)
            return intent
        }
    }
}
