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
import com.example.cuisinhelha.models.Review;

import java.util.List;

public class RecipeReviewAdapter extends ArrayAdapter<Review> {
    public RecipeReviewAdapter(@NonNull Context context, int resource, @NonNull List<Review> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.recipe_details_review_list, null);
        }
        final Review review = getItem(position);

        TextView tvUser = v.findViewById(R.id.tvUserNameReview);
        TextView tvRate = v.findViewById(R.id.tvRateReview);
        TextView tvReview = v.findViewById(R.id.tvReview);

        tvUser.setText(review.getPseudo());
        tvRate.setText(review.getRate()+"/5");
        tvReview.setText(review.getReviewMessage());

        return v;


    }
}
