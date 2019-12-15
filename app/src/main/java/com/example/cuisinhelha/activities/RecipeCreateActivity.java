package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.cuisinhelha.R;
import com.example.cuisinhelha.adapters.IngredientRecipeCreateAdapter;
import com.example.cuisinhelha.adapters.StepRecipeCreateAdapter;
import com.example.cuisinhelha.models.Ingredient;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.models.Step;
import com.example.cuisinhelha.services.IngredientRepositoryService;
import com.example.cuisinhelha.services.RecipeRepositoryService;
import com.example.cuisinhelha.services.StepRepositoryService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;

public class RecipeCreateActivity extends AppCompatActivity implements IHeaderNavigation {

    private EditText etName;
    private EditText etSummary;
    private EditText etTime;
    private EditText etPeople;
    private EditText etSpice;
    private EditText etQuantity;
    private EditText etStep;

    private Spinner spinRecipeType;
    private String[] types = {"Starter", "Main course", "Dessert"};
    private ArrayAdapter<String> typeAdapter;
    private Spinner spinIngredientName;
    private List<Ingredient> ingredientsName;
    private ArrayAdapter adapterIngredientName;
    private Spinner spinIngredientUnit;
    private String[] units = {"g", "L", "Pièce", "ml"};
    private ArrayAdapter<String> unitAdapter;

    private ListView lvIngredient;
    private List<Ingredient> ingredientsRecipe;
    private IngredientRecipeCreateAdapter adapterIngredientRecipe;
    private ListView lvStep;
    private List<Step> steps;
    private StepRecipeCreateAdapter adapterStepRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);

        initializeEditTexts();
        initializeSpins();
        initializeListViews();


    }

    private void initializeListViews() {

        lvIngredient = findViewById(R.id.recipe_ingredient_lv);
        ingredientsRecipe = new ArrayList<>();
        adapterIngredientRecipe = new IngredientRecipeCreateAdapter(this, R.id.recipe_ingredient_lv, ingredientsRecipe);
        lvIngredient.setAdapter(adapterIngredientRecipe);

        lvStep = findViewById(R.id.recipe_steps_lv);
        steps = new ArrayList<>();
        adapterStepRecipe = new StepRecipeCreateAdapter(this, R.id.recipe_steps_lv, steps);
        lvStep.setAdapter(adapterStepRecipe);
    }

    private void initializeSpins() {
        spinRecipeType = findViewById(R.id.recipe_type_spin);
        typeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, types);
        spinRecipeType.setAdapter(typeAdapter);

        spinIngredientName = findViewById(R.id.recipe_ingredient_name_spin);
        ingredientsName = new ArrayList<>();
        adapterIngredientName = new ArrayAdapter(this, R.layout.spinner_ingredient_item, ingredientsName);
        spinIngredientName.setAdapter(adapterIngredientName);
        spinIngredientName.setSelection(0);

        loadIngredients();

        spinIngredientUnit = findViewById(R.id.recipe_ingredient_unit_spin);
        unitAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, units);
        spinIngredientUnit.setAdapter(unitAdapter);

    }

    private void initializeEditTexts() {

        etName = findViewById(R.id.recipe_name_et);
        etSummary = findViewById(R.id.recipe_summary_et);
        etTime = findViewById(R.id.recipe_time_et);
        etPeople = findViewById(R.id.recipe_people_et);
        etSpice = findViewById(R.id.recipe_spice_et);
        etQuantity = findViewById(R.id.recipe_ingredient_quantity_et);
        etStep = findViewById(R.id.recipe_step_create_et);

        initializeEditTextsListeners();
    }

    private void initializeEditTextsListeners() {
        etSpice.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int spice = Integer.parseInt(etSpice.getText().toString());
                    if(spice < 0)
                    {etSpice.setText("0");
                    }else if(spice > 5)
                    {etSpice.setText("5");}
                }catch(Exception e){}
            }
        });

        etTime.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int time = Integer.parseInt(etTime.getText().toString());
                    if(time < 0)
                    {etTime.setText("0");}
                }catch(Exception e){}
            }
        });

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int quantity = Integer.parseInt(etQuantity.getText().toString());
                    if(quantity < 0)
                    {etQuantity.setText("0");}
                }catch(Exception e){}
            }
        });

        etPeople.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    int people = Integer.parseInt(etPeople.getText().toString());
                    if(people < 0)
                    {etPeople.setText("0");}
                }catch(Exception e){}
            }
        });

    }


    private void loadIngredients() {
        IngredientRepositoryService.query().enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                adapterIngredientName.clear();
                adapterIngredientName.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Log.wtf("query", "Une erreur est survenue lors de la connection avec la table 'ingredients'");
            }
        });
    }

    public void postRecipe(View view)
    {
        try {
            ///TODO remplacer le user
            final Recipe recipe = new Recipe(7,
                    etName.getText().toString(),
                    Calendar.getInstance().toString(),
                    etSummary.getText().toString(),
                    Integer.parseInt(etPeople.getText().toString()),
                    Integer.parseInt(etTime.getText().toString()),
                    Integer.parseInt(etSpice.getText().toString()),
                    spinRecipeType.getSelectedItem().toString());

            RecipeRepositoryService.post(recipe).enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                    postIngredients(response.body().getIdRecipe(), 0);
                }

                @Override
                public void onFailure(Call<Recipe> call, Throwable t) {
                    Log.wtf("post Recipe", "La recette n'a pas pu être postée");
                }
            });
        }catch(Exception e){
            ///TODO afficher une erreur de validité
        }

    }

    public void postIngredients(final int idRecipe, final int pos)
    {
        if(pos < ingredientsRecipe.size())
        {
            final int next = pos+1;
            ingredientsRecipe.get(pos).setIdRecipe(idRecipe);
            IngredientRepositoryService.postToRecipe(ingredientsRecipe.get(pos)).enqueue(new Callback<Ingredient>() {
                @Override
                public void onResponse(Call<Ingredient> call, Response<Ingredient> response) {

                    postIngredients(idRecipe, next);
                }

                @Override
                public void onFailure(Call<Ingredient> call, Throwable t) {
                    Log.wtf("post Ingredient", "un ingrédient n'a pas pu être posté");
                }
            });
        }else
        {
            postSteps(idRecipe, 0);
        }
    }

    public void postSteps(final int idRecipe, int pos)
    {
        if(pos < steps.size())
        {
            final int next = pos+1;
            steps.get(pos).setIdRecipe(idRecipe);
            StepRepositoryService.post(steps.get(pos)).enqueue(new Callback<Step>() {
                @Override
                public void onResponse(Call<Step> call, Response<Step> response) {
                    postSteps(idRecipe, next);
                }

                @Override
                public void onFailure(Call<Step> call, Throwable t) {
                    Log.wtf("post Ingredient", "une étape n'a pas pu être postée");
                }
            });
        }else
        {
            resetForm();
        }
    }

    public void addIngredient(View view) {


        try {
            Ingredient ingredient = ingredientsName.get(spinIngredientName.getSelectedItemPosition());
            ingredient.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
            ingredient.setUnit(spinIngredientUnit.getSelectedItem().toString());

            adapterIngredientRecipe.add(ingredient);

            resetIngredient();
            updateListViewSize(lvIngredient);
        }catch (Exception e){

        }

    }

    public void addStep(View view){
        Step step = new Step(-1, -1, -1, etStep.getText().toString());

        adapterStepRecipe.add(step);
        adapterStepRecipe.updateStepNb();

        resetStep();
        updateListViewSize(lvStep);
    }


    private void resetForm() {
        etName.setText("");
        etSummary.setText("");
        etPeople.setText("");
        etTime.setText("");
        etQuantity.setText("");

        spinRecipeType.setSelection(0);

        resetIngredient();
        resetStep();
    }

    private void resetIngredient() {
        spinIngredientName.setSelection(0);
        etQuantity.setText("");
        spinIngredientUnit.setSelection(0);
    }

    private void resetStep() {
        etStep.setText("");
    }

    private void updateListViewSize(ListView lv){
        int totalHeight = 0;
        for(int i = 0; i < lv.getAdapter().getCount(); i++)
        {
            Log.wtf("index", i+"");
            View listItem = lv.getAdapter().getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getAdapter().getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
    }

    @Override
    public void loadProfileActivity(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);           
    }

    @Override
    public void loadHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadRecipeSearchActivity(View view) {
        Intent intent = new Intent(this, RecipeSearchActivity.class);
        startActivity(intent);
    }
}