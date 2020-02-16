package com.koktajlbar.incognitobook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.DefaultCocktailRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailsViewModel @Inject constructor(private val defaultCocktailRepository: DefaultCocktailRepository) : ViewModel() {
    private val _cocktails = MutableLiveData<MutableList<Cocktail>>()
    val cocktails: MutableLiveData<MutableList<Cocktail>> = _cocktails

    init {
        viewModelScope.launch {
            setCocktailList(defaultCocktailRepository.allCocktails())
        }
    }

    fun setCocktailList(cocktailList: LiveData<MutableList<Cocktail>>) {
        this._cocktails.value = cocktailList.value
    }
}