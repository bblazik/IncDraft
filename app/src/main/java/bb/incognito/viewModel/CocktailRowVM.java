package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.view.View;

import bb.incognito.model.Cocktail;
import bb.incognito.view.CocktailDetail;
import bb.incognito.view.GuestDetail;

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
