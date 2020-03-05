package com.koktajlbar.incognitobook.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.koktajlbar.incognitobook.model.Cocktail;
import com.koktajlbar.incognitobook.view.CocktailDetailsActivity;


public class CocktailRowVM extends BaseObservable {

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
