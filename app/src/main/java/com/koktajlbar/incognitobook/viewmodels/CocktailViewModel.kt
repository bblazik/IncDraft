package com.koktajlbar.incognitobook.viewmodels

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.DefaultCocktailRepository
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import java.util.*

class CocktailViewModel(private val defaultCocktailRepository: DefaultCocktailRepository) : ViewModel() {
    private val _cocktail = MutableLiveData<Cocktail>()
    val cocktail: LiveData<Cocktail>? = _cocktail

    @BindingAdapter("bind:videoId")
    fun setYoutubeLink(youTubePlayerView: YouTubePlayerView, customVideoId: String) {
        youTubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                if (customVideoId !== "") {
                    Log.d("setYoutubeLink", "Custom video id is: " + customVideoId)
                    youTubePlayer.cueVideo(customVideoId, 0f)
                }
            }
        })
    }

    fun getVideoId(): String {
        Log.d("getVideoId", "Custom video id is: " + cocktail?.value?.videoId!!)
        return cocktail?.value?.videoId!!
    }

    fun hasVideo(): Boolean {
        Log.d("hasVideo", "video is visible: " + (!cocktail?.value?.videoId.orEmpty().isNullOrEmpty()).toString())
        return !cocktail?.value?.videoId.orEmpty().isNullOrEmpty()
    }

    fun getCocktail(uuid: UUID) {
        viewModelScope.launch {
            setCocktail(defaultCocktailRepository.getCocktail(uuid))
        }
    }

    fun setCocktail(cocktail: LiveData<Cocktail>) {
        this._cocktail.value = cocktail.value
    }
}