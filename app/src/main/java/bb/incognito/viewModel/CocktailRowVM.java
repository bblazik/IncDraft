package bb.incognito.viewModel;

import bb.incognito.model.Cocktail;

public class CocktailRowVM {

    private Cocktail cocktail;

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

    }
}
