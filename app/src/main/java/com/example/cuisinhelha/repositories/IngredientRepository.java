package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IngredientRepository {
    @GET(Configuration.API_INGREDIENT)
    Call<List<Ingredient>> query();

    @GET(Configuration.API_INGREDIENT + "recipe={id}")
    Call<List<Ingredient>> queryByRecipe(@Path("id") int id);

    @POST(Configuration.API_INGREDIENT)
    Call<Ingredient> post(@Body Ingredient ingredient);

    @POST(Configuration.API_INGREDIENT + "recipe")
    Call<Ingredient> postToRecipe(@Body Ingredient ingredient);

    @DELETE(Configuration.API_INGREDIENT + "{id}")
    Call<Void> delete(@Path("id") int id);

    @DELETE(Configuration.API_INGREDIENT + "recipe={id}")
    Call<Void> deleteFromRecipe(@Path("id") int id);

    @PUT(Configuration.API_INGREDIENT)
    Call<Void> put(@Body Ingredient ingredient);

    @PUT(Configuration.API_INGREDIENT + "recipe")
    Call<Void> putInRecipe(@Body Ingredient ingredient);

}
