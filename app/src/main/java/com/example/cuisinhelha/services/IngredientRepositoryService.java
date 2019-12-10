package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Ingredient;
import com.example.cuisinhelha.repositories.IngredientRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientRepositoryService {
    public static final IngredientRepositoryService instance = new IngredientRepositoryService();
    public static IngredientRepositoryService getInstance() {return instance;}

    private IngredientRepository repository;

    private IngredientRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IngredientRepository.class);
    }

    public IngredientRepository getRepository() {
        return repository;
    }

    public static Call<List<Ingredient>> query(){
        return instance.repository.query();
    }
    public static Call<List<Ingredient>> queryByRecipe(int id){return  instance.repository.queryByRecipe(id);}

    public static Call<Ingredient> post(Ingredient ingredient){return instance.repository.post(ingredient);}
    public static Call<Ingredient> postToRecipe(Ingredient ingredient){return instance.repository.postToRecipe(ingredient);}

    public static Call<Void> delete(int id){return  instance.repository.delete(id);}
    public static Call<Void> deleteFromRecipe(int id){return instance.repository.deleteFromRecipe(id);}

    public static Call<Void> put(Ingredient ingredient){return instance.repository.put(ingredient);}
    public static Call<Void> putInRecipe(Ingredient ingredient){return instance.repository.putInRecipe(ingredient);}
}
