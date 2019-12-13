package com.example.cuisinhelha.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.models.Ingredient;

import java.util.List;

public class IngredientRecipeCreateAdapter extends ArrayAdapter<Ingredient> {

    List<Ingredient> items;
    public IngredientRecipeCreateAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;


        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.ingredient_recipe_create_item, null);
        }

        TextView tvIngredient = v.findViewById(R.id.ingredient_tv);
        ImageButton btnDelete = v.findViewById(R.id.delete_ingredient_btn);

        tvIngredient.setText(getItem(position).getNameIngredient() + " : " + getItem(position).getQuantity() + getItem(position).getUnit());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(position));
            }
        });

        return v;
    }

    @Override
    public void add(@Nullable Ingredient object) {

        if(!items.contains(object)) {
            super.add(object);
        }else
        {
            notifyDataSetChanged();
        }

    }
}
