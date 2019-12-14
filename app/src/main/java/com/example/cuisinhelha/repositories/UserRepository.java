package com.example.cuisinhelha.repositories;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.Mail;
import com.example.cuisinhelha.models.Password;
import com.example.cuisinhelha.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserRepository {
    @GET(Configuration.API_USER)
    Call<List<User>> query();

    @POST(Configuration.API_USER)
    Call<User> post(@Body User user);

    @POST(Configuration.API_USER+"authenticate")
    Call<User> authenticate(@Body AuthenticateUser user);

    @PUT(Configuration.API_USER)
    Call<Void> put(@Body User user);

    @PUT(Configuration.API_USER+"password")
    Call<Void> putPassword(@Body Password password);

    @PUT(Configuration.API_USER+"mail")
    Call<Void> putMail(@Body Mail mail);

    @DELETE(Configuration.API_USER+"{id}")
    Call<Void> delete(@Path("id") int id);
}
