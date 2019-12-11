package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewRepository {
    @GET(Configuration.API_REVIEW)
    Call<List<Review>> query();

    @GET(Configuration.API_REVIEW + "recipe={id}")
    Call<List<Review>> queryByRecipe(@Path("id") int id);

    @GET(Configuration.API_REVIEW + "user={id}")
    Call<List<Review>> queryByUser(@Path("id") int id);

    @GET(Configuration.API_REVIEW + "average/{id}")
    Call<Float> queryAvgByRecipe(@Path("id") int id);

    @POST(Configuration.API_REVIEW)
    Call<Review> post(@Body Review review);

    @PUT(Configuration.API_REVIEW)
    Call<Void> put(@Body Review review);

    @DELETE(Configuration.API_REVIEW+"{idUser}/{idRecipe}")
    Call<Void> delete(@Path("idUser") int idUser, @Path("idRecipe") int idRecipe);

}
