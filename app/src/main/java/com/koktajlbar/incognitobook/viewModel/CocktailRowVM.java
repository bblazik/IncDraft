package com.koktajlbar.incognitobook.viewModel;

import androidx.databinding.BaseObservable;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.koktajlbar.incognitobook.view.CocktailDetailsActivity;


public class CocktailRowVM extends BaseObservable {

    public boolean extended = false;
    public boolean hasVideo = false;

    private Cocktail cocktail;
    private Context context;

    public CocktailRowVM(Cocktail cocktail, Context context) {
        this.cocktail = cocktail;
        this.context = context;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
        notifyChange();
    }

    public String getName(){ return cocktail.getName();}
    public String getIngredients(){ return cocktail.getIngredients();}
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
        Intent intent = new Intent(context, CocktailDetailsActivity.class);
        intent.putExtra("uuid", cocktail.getId());
        context.startActivity(intent);
    }

    public boolean onLongClick(View view)
    {
        return true;
    }
}
