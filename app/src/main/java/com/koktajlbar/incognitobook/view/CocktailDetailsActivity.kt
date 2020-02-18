package com.koktajlbar.incognitobook.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.viewModel.CocktailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.cocktail_details_activity.*
import java.util.*
import javax.inject.Inject


class CocktailDetailsActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CocktailViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cocktail_details_activity)
        val uuid: UUID = intent.getSerializableExtra("uuid") as UUID
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