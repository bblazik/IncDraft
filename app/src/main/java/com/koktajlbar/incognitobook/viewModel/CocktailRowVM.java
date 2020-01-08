package com.koktajlbar.incognitobook.viewModel;

import android.databinding.BaseObservable;
import android.view.View;

import com.koktajlbar.incognitobook.R;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class CocktailRowVM extends BaseObservable{

    private Cocktail cocktail;
    public boolean extended = false;
    public CocktailRowVM(Cocktail cocktail) {
        this.cocktail = cocktail;
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

    public String getYoutubeLink() { return cocktail.getYoutubeLink(); }

    public String getYoutubeVideoId() {
        String[] array = getYoutubeLink().split("=");
        return array[array.length - 1];
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
