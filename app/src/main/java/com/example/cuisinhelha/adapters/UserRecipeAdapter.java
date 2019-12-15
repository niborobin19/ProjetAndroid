package com.example.cuisinhelha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.services.ReviewRepositoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRecipeAdapter extends ArrayAdapter<Recipe> {
    public UserRecipeAdapter(@NonNull Context context, int resource, @NonNull List<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.user_recipes_item, null);
        }

        final Recipe recipe = getItem(position);

        TextView tvName = v.findViewById(R.id.user_recipe_name_tv);
        final TextView tvRate = v.findViewById(R.id.user_recipe_rate_tv);

        tvName.setText(recipe.getNameRecipe());
        ReviewRepositoryService.queryAvgByRecipe(recipe.getIdRecipe()).enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                tvRate.setText("("+response.body()+"/5)");
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                tvRate.setText("(?/5)");
            }
        });

        return v;
    }
}
