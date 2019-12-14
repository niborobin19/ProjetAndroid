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
import com.example.cuisinhelha.models.Step;

import java.util.List;

public class RecipeStepAdapter extends ArrayAdapter<Step> {

    public RecipeStepAdapter(@NonNull Context context, int resource, @NonNull List<Step> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.recipe_details_step_list, null);
        }

        final Step step = getItem(position);

        TextView tvStep = v.findViewById(R.id.tvStep);

        tvStep.setText(step.getStepNb()+" "+step.getStep());

        return v;
    }
}
