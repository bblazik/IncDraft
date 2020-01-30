package com.koktajlbar.incognitobook.view

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewModel.CocktailViewModel
import com.koktajlbar.incognitobook.viewModel.CocktailViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import kotlinx.android.synthetic.main.cocktail_details_activity.*
import kotlinx.android.synthetic.main.cocktail_details_activity.view.*
import java.util.*


class CocktailDetailsActivity : AppCompatActivity() {
    private var viewModelFactory: CocktailViewModelFactory? = null
    private var viewModel: CocktailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocktail_details_activity)
        val uuid: UUID = intent.getSerializableExtra("uuid") as UUID
        this.viewModelFactory = CocktailViewModelFactory(application)
        this.viewModel = ViewModelProvider(this, viewModelFactory!!).get(CocktailViewModel::class.java)
        viewModel!!.getCocktail(uuid)
        lifecycle.addObserver(youtube_player_view)
        val cocktailObserver = Observer<Cocktail> { cocktail ->
            collapsingToolbar.title = cocktail.name
            youtube_player_view.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    if (cocktail.videoId.isNotBlank()) {
                        youtube_player_view.visibility = View.VISIBLE
                        youTubePlayer.cueVideo(cocktail.videoId,0f)
                    }
                }
            })
            cocktailDetailsCategory.text = cocktail.category
            cocktailDetailsGarnish.text = cocktail.garnish
            cocktailDetailsGlassware.text = cocktail.glassware
            cocktailDetailsTechnique.text = cocktail.technique
            cocktailDetailsIngredients.text = cocktail.ingredients
        }


        viewModel!!.cocktail?.observe(this, cocktailObserver)
    }
}