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
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.services.ReviewRepositoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSearchResultAdapter extends ArrayAdapter<Recipe> {
    public RecipeSearchResultAdapter(@NonNull Context context, int resource, @NonNull List<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.wtf("randomStart", ""+position);
        Log.wtf("item", getItem(position).toString());
        Log.wtf("randomEnd", ""+position);

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.recipe_search_result, null);
        }

        final Recipe recipe = getItem(position);

        TextView tvName = v.findViewById(R.id.recipeNameTv);
        final TextView tvRateAvg = v.findViewById(R.id.rateAvgTv);
        TextView tvDescription =  v.findViewById(R.id.descriptionTv);
        TextView tvSummary = v.findViewById(R.id.summaryTv);
        TextView tvInfo = v.findViewById(R.id.infoTv);

        tvName.setText(recipe.getNameRecipe());
        tvDescription.setText( recipe.getRecipeType() + " for " + recipe.getPersons() + " people (" + recipe.getPrepTime() + " min)");
        tvSummary.setText(recipe.getSummary());
        tvInfo.setText("Posted on " + recipe.getPostDate() + " by " + recipe.getPseudo());

        ReviewRepositoryService.queryAvgByRecipe(recipe.getIdRecipe())
                .enqueue(new Callback<Float>() {
                    @Override
                    public void onResponse(Call<Float> call, Response<Float> response) {
                        tvRateAvg.setText("("+response.body().toString()+"/5)");
                    }

                    @Override
                    public void onFailure(Call<Float> call, Throwable t) {
                        tvRateAvg.setText("(?/5)");
                    }
                });
        return v;
    }
}
