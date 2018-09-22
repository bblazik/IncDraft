package bb.incognito.view.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.R;
import bb.incognito.databinding.CocktailRowBinding;
import bb.incognito.model.Cocktail;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.viewModel.CocktailRowVM;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> implements Filterable {
    private List<Cocktail> cocktails = new ArrayList<>();
    private List<Cocktail> filteredCocktails = new ArrayList<>();
    private CocktailAdapter.ItemFilter filter = new CocktailAdapter.ItemFilter();

    public void setCocktailList(List<Cocktail> cocktailList) {
        cocktails = cocktailList;
        filteredCocktails = cocktailList;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        filteredCocktails.remove(position);
        notifyItemRemoved(position);
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
        holder.bindCard(filteredCocktails.get(position));
        holder.itemView.setOnContextClickListener(view -> {
            itemTouchHelper.startSwipe(holder);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return filteredCocktails.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
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

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Cocktail> newList = new ArrayList<>(cocktails.size());

            for(Cocktail c : cocktails)
            {
                if(c.getName().toLowerCase().contains(constraint))
                    newList.add(c);
            }
            results.values = newList;
            results.count = newList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredCocktails = (ArrayList<Cocktail>) results.values;
            notifyDataSetChanged();
        }
    }
}
