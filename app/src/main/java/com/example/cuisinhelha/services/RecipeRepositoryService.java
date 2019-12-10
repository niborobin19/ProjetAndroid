package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Recipe;
import com.example.cuisinhelha.repositories.RecipeRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeRepositoryService {
    public static final RecipeRepositoryService instance = new RecipeRepositoryService();
    public static RecipeRepositoryService getInstance() {return instance;}

    private RecipeRepository repository;

    private RecipeRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeRepository.class);
    }

    public RecipeRepository getRepository() {
        return repository;
    }

    public static Call<List<Recipe>> query() {return instance.repository.query();}
    public static Call<List<Recipe>> queryByRecipe(int id){return instance.repository.queryById(id);}
    public static Call<List<Recipe>> queryByUser(int id){return instance.repository.queryByUser(id);}
    public static Call<List<Recipe>> queryText(String text){return instance.repository.queryText(text);}
    public static Call<List<Recipe>> queryPseudo(){return instance.repository.queryPseudo();}

    public static Call<Recipe> post(Recipe recipe){return instance.repository.post(recipe);}

    public static Call<Void> put(Recipe recipe){return instance.repository.put(recipe);}

    public static Call<Void> delete(int id){return instance.repository.delete(id);}

}
