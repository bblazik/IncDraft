package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.view.AddCocktailFragment;

public class AddCocktailVM extends BaseObservable{

    CocktailRepository cocktailRepository;
    AddCocktailFragment df;
    Guest guest;

    public String name = "";
    public List<String> tags = new ArrayList<>();
    public String notes = "";
    public String others = "";

    boolean edit = false;
    int position = -1;

    public String getTags() {
        return tags.toString().replaceAll("\\[?\\]?,?", "");
    }

    public void setTags(String tags) {
        this.tags = (Arrays.asList(tags.split(" ")));
    }

    public AddCocktailVM(AddCocktailFragment dialogFragment, boolean editCocktail, int pos, CocktailRepository cocktailRepository, Guest guest){
        position = pos;
        edit = editCocktail;
        this.cocktailRepository = cocktailRepository;
        this.df = dialogFragment;
        this.guest = guest;
    }

    public View.OnClickListener acceptButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(fieldsNotEmpty()) {
                Cocktail newCocktail = new Cocktail(name, guest.getId());
                guest.addCocktail(newCocktail);
                cocktailRepository.insert(newCocktail);
                notifyChange();
                df.dismiss();
            }else {
                Toast.makeText(v.getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View.OnClickListener closeButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            df.dismiss();
        }
    };

    public boolean fieldsNotEmpty(){
        return !name.equals("");
    }
}
