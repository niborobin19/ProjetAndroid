package com.example.cuisinhelha.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.example.cuisinhelha.helpers.UserPreferences;
import com.example.cuisinhelha.models.Ingredient;
import com.example.cuisinhelha.models.Picture;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.models.Step;
import com.example.cuisinhelha.services.IngredientRepositoryService;
import com.example.cuisinhelha.services.PictureRepositoryService;
import com.example.cuisinhelha.services.RecipeRepositoryService;
import com.example.cuisinhelha.services.StepRepositoryService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.cuisinhelha.interfaces.IHeaderNavigation;

public class RecipeCreateActivity extends AppCompatActivity implements IHeaderNavigation {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FILE = 2;

    private SharedPreferences pref;

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

    private Picture picture = new Picture(-1, "");
    private ImageView ivPicture;
    private String picturePath;
    private Uri pictureUri;
    private File pictureFile;
    private Bitmap pictureBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);

        pref = getSharedPreferences(UserPreferences.PREFERENCES_NAME, MODE_PRIVATE);

        initializeEditTexts();
        initializeSpins();
        initializeListViews();
        ivPicture = findViewById(R.id.recipe_picture_iv);


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
            final Recipe recipe = new Recipe(pref.getInt(UserPreferences.ID_USER, 7),
                    etName.getText().toString(),
                    new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()),
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
            postPicture(idRecipe);
        }
    }

    public void postPicture(final int idRecipe)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        byte[] file = Base64.encode(data, Base64.DEFAULT);
        String filecode = new String(file);

        picture.setIdRecipe(idRecipe);
        picture.setPicture(filecode);

        PictureRepositoryService.post(picture).enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                Picture respPicture = response.body();
                byte[] data = Base64.decode(respPicture.getPicture(), Base64.DEFAULT);
                Bitmap respImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                ivPicture.setImageBitmap(respImage);
                resetForm();
            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {

            }
        });
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

        if(step.getStep() != "") {
            adapterStepRecipe.add(step);
            adapterStepRecipe.updateStepNb();
            resetStep();
            updateListViewSize(lvStep);
        }
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

    public void loadTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void loadOpenPictureIntent(View view){
        Intent filePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        filePictureIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(filePictureIntent, "Select picture"), REQUEST_IMAGE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.wtf("datas", extras.toString());
            pictureBitmap = (Bitmap) extras.get("data");
            ivPicture.setImageBitmap(pictureBitmap);
            // Create the File where the photo should go
            pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Log.wtf("Error", "Le fichier Image n'a pas pu être créé");
            }
            // Continue only if the File was successfully created
            if (pictureFile != null) {
                pictureUri = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        pictureFile);
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

        if(requestCode == REQUEST_IMAGE_FILE && resultCode == RESULT_OK)
        {

            try {
                pictureUri = data.getData();
                pictureBitmap = BitmapFactory.decodeFileDescriptor(getContentResolver().openFileDescriptor(pictureUri, "r").getFileDescriptor());
                ivPicture.setImageBitmap(pictureBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        picturePath = image.getAbsolutePath();
        return image;
    }
}