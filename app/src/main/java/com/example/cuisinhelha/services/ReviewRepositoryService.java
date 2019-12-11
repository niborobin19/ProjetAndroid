package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Review;
import com.example.cuisinhelha.repositories.ReviewRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewRepositoryService {
    public static final ReviewRepositoryService instance = new ReviewRepositoryService();
    public static ReviewRepositoryService getInstance() {return instance;}

    private ReviewRepository repository;

    private ReviewRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReviewRepository.class);
    }

    public ReviewRepository getRepository() {
        return repository;
    }

    public static Call<List<Review>> query(){return instance.repository.query();}
    public static Call<List<Review>> queryByRecipe(int id){return instance.repository.queryByRecipe(id);}
    public static Call<List<Review>> queryByUser(int id){return instance.repository.queryByUser(id);}
    public static Call<Float> queryAvgByRecipe(int id){return instance.repository.queryAvgByRecipe(id);}

    public static Call<Review> post(Review review){return instance.repository.post(review);}

    public static Call<Void> put(Review review){return instance.repository.put(review);}

    public static Call<Void> delete(int idUser, int idRecipe){return instance.repository.delete(idUser, idRecipe);}
}
