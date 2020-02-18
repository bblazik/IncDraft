package com.koktajlbar.incognitobook.viewModel

import androidx.lifecycle.*
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.DefaultCocktailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailsViewModel @Inject constructor(private val defaultCocktailRepository: DefaultCocktailRepository) : ViewModel() {
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