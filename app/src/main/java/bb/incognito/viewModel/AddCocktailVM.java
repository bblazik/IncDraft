package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.view.AddCocktailFragment;
import bb.incognito.view.adapter.CocktailAdapter;

public class AddCocktailVM extends BaseObservable{

    CocktailAdapter cocktailAdapter;
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

    public AddCocktailVM(AddCocktailFragment dialogFragment, boolean editCocktail, int pos, CocktailAdapter cocktailAdapter, Guest guest){
        position = pos;
        edit = editCocktail;
        this.cocktailAdapter = cocktailAdapter;
        this.df = dialogFragment;
        this.guest = guest;
        //For edit.
//        if(position >=0) {
//            edit = CocktailAdapter.getInstance().getCocktailAtPos(position);
//            location = edit.location;
//            cocktail = edit.cocktail;
//        }
    }

    public View.OnClickListener acceptButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(edit){
//                Cocktail newCocktail = new Cocktail(location, cocktail);
//                CocktailAdapter.getInstance().editItemOnPos(position, newCocktail);
//                //CocktailAdapter.getInstance().addCocktailToList(newCocktail);
//                df.dismiss();
//                return;
//            }

            if(fieldsNotEmpty()) {
                Cocktail newCocktail = new Cocktail(name, tags, notes, others);
                cocktailAdapter.addCocktailToList(newCocktail);
                guest.addCocktail(newCocktail);
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
