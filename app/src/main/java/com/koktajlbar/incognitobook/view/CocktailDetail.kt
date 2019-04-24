package com.koktajlbar.incognitobook.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentCocktailDetailBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.viewModel.CocktailRowVM


class CocktailDetail : AppCompatActivity() {

    var binding: FragmentCocktailDetailBinding? = null
    var vm: CocktailRowVM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cocktail_detail)
        initDataBinding()
    }

    fun initDataBinding() {
        var cocktail = intent.extras.getParcelable("Cocktail") as Cocktail

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_cocktail_detail)
        vm = CocktailRowVM(cocktail)
        binding!!.vm = vm
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
        fun launchDetail(context: Context, cocktail: Cocktail): Intent {
            val intent = Intent(context, CocktailDetail::class.java)
            intent.putExtra("Cocktail", cocktail)
            return intent
        }
    }
}



