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

public class UserReviewAdaper extends ArrayAdapter<Review> {

    public UserReviewAdaper(@NonNull Context context, int resource, @NonNull List<Review> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.user_reviews_item, null);
        }

        final Review review = getItem(position);

        TextView tvName = v.findViewById(R.id.user_review_recipe_tv);
        TextView tvRate = v.findViewById(R.id.user_review_rate_tv);
        TextView tvReview = v.findViewById(R.id.user_review_tv);

        tvName.setText(review.getNameRecipe());
        tvRate.setText("("+review.getRate()+"/5)");
        tvReview.setText(review.getReviewMessage());

        return v;
    }
}
