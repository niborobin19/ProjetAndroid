package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeRepository {
    @GET(Configuration.API_RECIPE)
    Call<List<Recipe>> query();

    @GET(Configuration.API_RECIPE + "pseudo")
    Call<List<Recipe>> queryPseudo();

    @GET(Configuration.API_RECIPE + "text={text}")
    Call<List<Recipe>> queryText(@Path("text") String text);

    @GET(Configuration.API_RECIPE + "id={id}")
    Call<List<Recipe>> queryById(@Path("id") int id);

    @GET(Configuration.API_RECIPE + "user={id}")
    Call<List<Recipe>> queryByUser(@Path("id") int id);

    @POST(Configuration.API_RECIPE)
    Call<Recipe> post(@Body Recipe recipe);

    @PUT(Configuration.API_RECIPE)
    Call<Void> put(@Body Recipe recipe);

    @DELETE(Configuration.API_RECIPE + "{id}")
    Call<Void> delete(@Path("id") int id);

}
