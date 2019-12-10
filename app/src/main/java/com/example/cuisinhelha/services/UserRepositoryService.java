package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Mail;
import com.example.cuisinhelha.models.Password;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.repositories.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepositoryService {
    public static final UserRepositoryService instance = new UserRepositoryService();
    public static UserRepositoryService getInstance() {return instance;}

    private UserRepository repository;

    private UserRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserRepository.class);
    }

    public UserRepository getRepository() {
        return repository;
    }

    public static Call<List<User>> query(){return instance.repository.query();}

    public static Call<User> post(User user){return instance.repository.post(user);}

    public static Call<Void> put(User user){return instance.repository.put(user);}
    public static Call<Void> putPassword(Password password){return instance.repository.putPassword(password);}
    public static Call<Void> putMail(Mail mail){return instance.repository.putMail(mail);}

}
