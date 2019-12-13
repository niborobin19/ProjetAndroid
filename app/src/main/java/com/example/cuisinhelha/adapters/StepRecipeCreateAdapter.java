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
import com.example.cuisinhelha.models.Step;

import java.util.List;

public class StepRecipeCreateAdapter extends ArrayAdapter<Step>{
    private List<Step> items;
    public StepRecipeCreateAdapter(@NonNull Context context, int resource, @NonNull List<Step> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;


        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.step_recipe_create_item, null);
        }

        TextView tvStep = v.findViewById(R.id.step_tv);
        ImageButton btnDelete = v.findViewById(R.id.delete_step_btn);
        Log.wtf("ingredient", getItem(position).toString());

        tvStep.setText(getItem(position).getStepNb() + " : " + getItem(position).getStep());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(position));
                updateStepNb();
            }
        });

        return v;
    }

    public void updateStepNb() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setStepNb(i+1);
        }
        notifyDataSetChanged();
    }
}
