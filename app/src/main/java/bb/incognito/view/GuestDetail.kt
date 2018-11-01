package bb.incognito.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import bb.incognito.R
import bb.incognito.model.GuestWithCocktails

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
