package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.Mail;
import com.example.cuisinhelha.models.Password;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.repositories.UserRepository;
import com.example.cuisinhelha.utils.SHA256Hasher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepositoryService {
    public static final UserRepositoryService instance = new UserRepositoryService();

    public static UserRepositoryService getInstance() {
        return instance;
    }

    private UserRepository repository;

    private UserRepositoryService() {
        init();
    }

    private void init() {
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserRepository.class);
    }

    public UserRepository getRepository() {
        return repository;
    }

    public static Call<List<User>> query() {
        return instance.repository.query();
    }

    public static Call<User> hashAndAuthenticate(AuthenticateUser user) {
        if (user.getUsername().length() < 1 || user.getPassword().length() < 1)
            return null;

        String hashedPsw = SHA256Hasher.hash(user.getPassword());
        user.setPassword(hashedPsw);

        return authenticate(user);
    }

    ;

    public static Call<User> authenticate(AuthenticateUser user) {
        if (user.getUsername().length() < 1 || user.getPassword().length() < 1)
            return null;

//        System.out.println(user.getUsername() + " " + user.getPassword());
        return instance.repository.authenticate(user);
    }

    ;

    public static Call<User> post(User user) {
        return instance.repository.post(user);
    }

    public static Call<Void> put(User user) {
        return instance.repository.put(user);
    }

    public static Call<Void> putPassword(Password password) {
        return instance.repository.putPassword(password);
    }

    public static Call<Void> putMail(Mail mail) {
        return instance.repository.putMail(mail);
    }

}
