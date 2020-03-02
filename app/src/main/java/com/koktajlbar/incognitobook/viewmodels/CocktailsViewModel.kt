package com.koktajlbar.incognitobook.viewmodels

import androidx.lifecycle.*
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.DefaultCocktailRepository
import kotlinx.coroutines.launch

class CocktailsViewModel(private val defaultCocktailRepository: DefaultCocktailRepository) : ViewModel() {
    private val _cocktails = MutableLiveData<MutableList<Cocktail>>()
    val cocktails: MutableLiveData<MutableList<Cocktail>> = _cocktails

    init {
        viewModelScope.launch {
            val allCocktails = defaultCocktailRepository.allCocktails()
            setCocktailList(allCocktails)
        }
    }

    fun setCocktailList(cocktailList: LiveData<MutableList<Cocktail>>) {
        this._cocktails.value = cocktailList.value
    }
}