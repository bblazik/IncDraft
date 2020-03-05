package com.koktajlbar.incognitobook.viewmodels

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.view.CocktailDetailsActivity

class CocktailRowVM(private var cocktail: Cocktail) : BaseObservable() {
    fun setCocktail(cocktail: Cocktail) {
        this.cocktail = cocktail
        notifyChange()
    }

    val name: String
        get() = cocktail.name

    fun onItemClick(view: View?) {
        val context = view?.context
        val intent = Intent(context, CocktailDetailsActivity::class.java)
        intent.putExtra("uuid", cocktail.id)
        context!!.startActivity(intent)
    }

    fun onLongClick(view: View?): Boolean {
        return true
    }

}