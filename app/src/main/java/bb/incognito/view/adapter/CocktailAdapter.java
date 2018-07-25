package bb.incognito.view.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.R;
import bb.incognito.databinding.CocktailRowBinding;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Cocktail;
import bb.incognito.view.GuestDetail;
import bb.incognito.viewModel.CocktailRowVM;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>{

    private List<Cocktail> displayList = new ArrayList<>();
    private List<Cocktail> originalData = new ArrayList<>();

    public void setCocktailList(List<Cocktail> cocktailList) {
        displayList = cocktailList;
        originalData = displayList;
        notifyDataSetChanged();
    }
    public void addCocktailToList(Cocktail cocktail){
        displayList.add(cocktail);
        //originalData.add(cocktail);
        notifyDataSetChanged();
    }

    @Override
    public CocktailAdapter.CocktailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CocktailRowBinding cardRowBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cocktail_row,
                        parent, false);
        return new CocktailAdapter.CocktailViewHolder(cardRowBinding);
    }

    @Override
    public void onBindViewHolder(CocktailAdapter.CocktailViewHolder holder, int position) {
        holder.bindCard(displayList.get(position));
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public int filterByName(String query){ //Can be replaced with filter interface. But idk why would I.
        List<Cocktail> filteredList = new ArrayList<>();
        query = query.replaceAll("\\s+","").toLowerCase();
        for(Cocktail c : originalData) { // due to lack of lambda in api<24
            if(cocktailNameContains(c, query)){
                filteredList.add(c);
            }
        }
        displayList = filteredList;
        notifyDataSetChanged();
        return displayList.size();
    }

    boolean cocktailNameContains(Cocktail g, String query){
        return g.getName().toLowerCase().contains(query);
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder {
        CocktailRowBinding cocktailRowBinding;
        Drawable settings;

        public CocktailViewHolder(CocktailRowBinding cocktailRowBinding) {
            super(cocktailRowBinding.cocktailRow);
            this.cocktailRowBinding = cocktailRowBinding;
        }

        void bindCard(Cocktail cocktail) {
            if (cocktailRowBinding.getViewModel() == null) {
                cocktailRowBinding.setViewModel(
                        new CocktailRowVM(cocktail));
            } else {
                cocktailRowBinding.getViewModel().setCocktail(cocktail);
            }
        }
        public void onItemSelected() {
            settings = itemView.getBackground();
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemClear() {
            itemView.setBackground(settings);
        }
    }
}
