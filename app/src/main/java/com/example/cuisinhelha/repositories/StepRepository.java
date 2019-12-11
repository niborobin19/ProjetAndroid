package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Step;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StepRepository {
    @GET(Configuration.API_STEP)
    Call<List<Step>> query();

    @GET(Configuration.API_STEP + "{id}")
    Call<List<Step>> queryByRecipe(@Path("id") int id);

    @POST(Configuration.API_STEP)
    Call<Step> post(@Body Step step);

    @PUT(Configuration.API_STEP)
    Call<Void> put(@Body Step step);

    @DELETE(Configuration.API_STEP + "{id}")
    Call<Void> delete(@Path("id") int id);
}
