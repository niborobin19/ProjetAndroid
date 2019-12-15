package com.example.cuisinhelha.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.activities.RecipeDetail;
import com.example.cuisinhelha.models.Review;
import com.example.cuisinhelha.services.RecipeRepositoryService;
import com.example.cuisinhelha.services.ReviewRepositoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeReviewAdapter extends ArrayAdapter<Review> {
    public RecipeReviewAdapter(@NonNull Context context, int resource, @NonNull List<Review> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.recipe_details_review_list, null);
        }
        final Review review = getItem(position);

        TextView tvUser = v.findViewById(R.id.tvUserNameReview);
        TextView tvRate = v.findViewById(R.id.tvRateReview);
        TextView tvReview = v.findViewById(R.id.tvReview);
        ImageButton btnDelete = v.findViewById(R.id.deleteReview);

        ///TODO masquer si l'utilisateur n'est ni admin ni propriétaire
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewRepositoryService.delete(getItem(position).getIdUser(), getItem(position).getIdRecipe()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.wtf(getItem(position).getReviewMessage(), getItem(position).getIdRecipe() + "");
                        remove(getItem(position));

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.wtf("delete recipe", "la recette n'a pas pu être supprimée");
                    }
                });
            }
        });

        tvUser.setText(review.getPseudo());
        tvRate.setText(review.getRate()+"/5");
        tvReview.setText(review.getReviewMessage());

        return v;


    }
}
