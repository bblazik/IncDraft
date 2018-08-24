package bb.incognito.view;
import android.app.DialogFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import bb.incognito.R;
import bb.incognito.databinding.FragmentAddCocktailBinding;
import bb.incognito.model.Guest;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.view.adapter.CocktailAdapter;
import bb.incognito.viewModel.AddCocktailVM;

import com.google.android.gms.plus.PlusOneButton;

public class AddCocktailFragment extends DialogFragment {

    FragmentAddCocktailBinding fragmentAddCocktailBinding;
    static CocktailRepository cocktailRepository;
    public AddCocktailFragment(){}
    static Guest guest;

    public static AddCocktailFragment newInstance(boolean edit, int pos, CocktailRepository cR, Guest _guest){
        AddCocktailFragment addCocktailFragment = new AddCocktailFragment();
        Bundle args = new Bundle();
        args.putBoolean("edit", edit);
        args.putInt("pos", pos);
        addCocktailFragment.setArguments(args);
        cocktailRepository = cR;
        guest = _guest;
        return addCocktailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentAddCocktailBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_cocktail, container, false);

        int pos = getArguments().getInt("pos");
        boolean edit = getArguments().getBoolean("edit");

        fragmentAddCocktailBinding.setViewModel(new AddCocktailVM(this,edit, pos, cocktailRepository, guest));
        return fragmentAddCocktailBinding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();

        fragmentAddCocktailBinding.newName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void dismiss(){
        ((InputMethodManager) getDialog().getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getView().getWindowToken(), 0);
        super.dismiss();
    }
}
