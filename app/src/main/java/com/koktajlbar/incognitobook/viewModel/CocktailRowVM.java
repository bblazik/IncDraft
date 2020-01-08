package com.koktajlbar.incognitobook.viewModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import android.view.View;

import com.koktajlbar.incognitobook.R;
import com.koktajlbar.incognitobook.databinding.FragmentCocktailDetailBinding;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class CocktailRowVM extends BaseObservable {

    public boolean extended = false;
    public boolean hasVideo = false;

    private Cocktail cocktail;

    public CocktailRowVM(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    @BindingAdapter("bind:videoId")
    public static void setYoutubeLink(YouTubePlayerView youTubePlayerView, String customVideoId) {
        youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
            @Override
            public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
                if(customVideoId != "") {
                    youTubePlayer.cueVideo(customVideoId, 0);
                }
            }
        });
    }

    @Bindable
    public boolean isExtendedAndHasAVideo() {
        boolean isExtended = this.extended;
        boolean hasVideo = getYoutubeLink().isEmpty();

        return isExtended && !hasVideo;
    }

    public String getYoutubeLink() {
        String videoUrl = cocktail.getYoutubeLink();
        if(videoUrl == null) {
            return "";
        } else {
            String[] array = cocktail.getYoutubeLink().split("=");
            return array[array.length - 1];
        }
    }

    public String getName(){ return cocktail.getName();}
    public String getTags() {
        return cocktail.getTags().toString().replaceAll("\\[?\\]?,?", "");
    }
    public String getNotes(){return cocktail.getNotes();}
    public String getOthers(){return cocktail.getOther();}

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
        notifyChange();
    }

    public String getIngredients(){return cocktail.getIngredients();}
    public String getGlassware() { return cocktail.getGlassware(); }
    public String getGarnish() { return cocktail.getGarnish(); }
    public String getTechnique() {
        return cocktail.getTechnique();
    }
    public String getSignature() {
        return String.valueOf(cocktail.getSignature());
    }
    public String getCategory() {
        return cocktail.getCategory();
    }

    public boolean getSignatureDescription() {
        return cocktail.getSignature();
    }

    public void onItemClick(View view) {
        extended = !extended;
        notifyChange();
        //view.getContext().startActivity(CocktailDetail.Companion.launchDetail(view.getContext(), cocktail));
    }

    public boolean onLongClick(View view)
    {
        return true;
    }
}
