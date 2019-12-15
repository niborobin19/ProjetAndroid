package com.example.cuisinhelha.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.models.Ingredient;
import com.example.cuisinhelha.models.Recipe;

import java.util.List;

public class RecipeIngredientAdapter extends ArrayAdapter<Ingredient> {

    public RecipeIngredientAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Log.wtf("ADAPTEUR INGREDIENT", position+"");
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.recipe_details_ingredient_list, null);
        }

        final Ingredient ingredient = getItem(position);


        TextView tvIngredientName = v.findViewById(R.id.ingredientName);
        TextView tvIngredientQuantity = v.findViewById(R.id.ingredientQuantity);
        TextView tvIngredientUnit = v.findViewById(R.id.ingredientUnit);

        tvIngredientName.setText(ingredient.getNameIngredient());
        tvIngredientQuantity.setText(ingredient.getQuantity()+"");
        tvIngredientUnit.setText(ingredient.getUnit());

        return v;
    }
}
