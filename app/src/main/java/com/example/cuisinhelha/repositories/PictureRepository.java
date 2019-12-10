package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Picture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PictureRepository {
    @GET(Configuration.API_PICTURE)
    Call<List<Picture>> query();

    @GET(Configuration.API_PICTURE + "{id}")
    Call<List<Picture>> queryByRecipe(@Path("id") int id);

    @POST(Configuration.API_PICTURE)
    Call<Picture> post(@Body Picture picture);

    @PUT(Configuration.API_PICTURE)
    Call<Void> put(@Body Picture picture);

    @DELETE(Configuration.API_PICTURE + "{id}")
    Call<Void> delete(@Path("id") int id);
}
