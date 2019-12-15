package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.helpers.UserPattern;
import com.example.cuisinhelha.models.AnswerDB;
import com.example.cuisinhelha.models.AuthenticateUser;
import com.example.cuisinhelha.models.MailUser;
import com.example.cuisinhelha.models.PasswordUser;
import com.example.cuisinhelha.models.User;
import com.example.cuisinhelha.repositories.UserRepository;
import com.example.cuisinhelha.utils.SHA256Hasher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserRepositoryService {
    public static final UserRepositoryService instance = new UserRepositoryService();
    public static String USER_TOKEN = "";
    private UserRepository repository;

    private UserRepositoryService() {
        init();
    }

    public static UserRepositoryService getInstance() {
        return instance;
    }

    public static Call<List<User>> query() {
        return instance.repository.query();
    }

    public static Call<User> hashAndAuthenticate(AuthenticateUser user) {
        String hashedPsw = SHA256Hasher.hash(user.getPassword());
        user.setPassword(hashedPsw);

        return authenticate(user);
    }

    public static Call<User> authenticate(AuthenticateUser user) {
        return instance.repository.authenticate(user);
    }

    public static Call<AnswerDB> post(User user) {
        return instance.repository.post(user);
    }

    public static Call<Void> put(User user) {
        return instance.repository.put(user);
    }

//    public static Call<Boolean> putPassword(PasswordUser user) {
//        return instance.repository.putPassword(USER_TOKEN, user);
//    }

    public static Call<Boolean> putPassword(PasswordUser user) {
        return instance.repository.putPassword(UserRepositoryService.USER_TOKEN, user);
    }


    public static Call<Boolean> putMail(MailUser user) {
        return instance.repository.putMail(USER_TOKEN, user);
    }

    public static boolean canAuthenticate(AuthenticateUser user) {
        if (user.getUsername().length() < 3 || user.getPassword().length() < 3)
            return false;

        if (!UserPattern.validatePseudo(user.getUsername()))
            return false;

        if (!UserPattern.validatePassword(user.getPassword()))
            return false;

        return true;
    }

    private void init() {
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserRepository.class);
    }

    public UserRepository getRepository() {
        return repository;
    }
}
