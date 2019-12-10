package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Picture;
import com.example.cuisinhelha.repositories.PictureRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PictureRepositoryService {

    public static final PictureRepositoryService instance = new PictureRepositoryService();
    public static PictureRepositoryService getInstance() {return instance;}

    private PictureRepository repository;

    private PictureRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PictureRepository.class);
    }

    public PictureRepository getRepository() {
        return repository;
    }

    public static Call<List<Picture>> query(){return instance.repository.query();}
    public static Call<List<Picture>> queryByRecipe(int id){return instance.repository.queryByRecipe(id);}

    public static Call<Picture> post(Picture picture){return instance.repository.post(picture);}

    public static Call<Void> put(Picture picture){return instance.repository.put(picture);}

    public static Call<Void> delete(int id){return instance.repository.delete(id);}
}
