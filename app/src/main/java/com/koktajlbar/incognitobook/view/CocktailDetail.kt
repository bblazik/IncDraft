package com.koktajlbar.incognitobook.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentCocktailDetailBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.viewmodels.CocktailRowVM

class CocktailDetail : AppCompatActivity() {
    private var binding: FragmentCocktailDetailBinding? = null
    private var vm: CocktailRowVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cocktail_detail)
        initDataBinding()
    }

    private fun initDataBinding() {
        val cocktail = intent.extras.getParcelable("Cocktail") as Cocktail

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_cocktail_detail)
        vm = CocktailRowVM(cocktail, applicationContext)
        binding!!.vm = vm
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}



