package com.koktajlbar.incognitobook.viewModel

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.*

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
    private val cocktailRepository: CocktailRepository = CocktailRepository(application)
    var cocktail: LiveData<Cocktail>? = null

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
        this.cocktail = cocktailRepository.getCocktail((uuid))
    }
}