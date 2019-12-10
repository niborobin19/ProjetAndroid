package com.example.cuisinhelha.services;

import com.example.cuisinhelha.Configuration;
import com.example.cuisinhelha.models.Step;
import com.example.cuisinhelha.repositories.StepRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepRepositoryService {
    public static final StepRepositoryService instance = new StepRepositoryService();
    public static StepRepositoryService getInstance() {return instance;}

    private StepRepository repository;

    private StepRepositoryService() {
        init();
    }

    private void init(){
        repository = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StepRepository.class);
    }

    public StepRepository getRepository() {
        return repository;
    }

    public static Call<List<Step>> query(){return instance.repository.query();}
    public static Call<List<Step>> queryByRecipe(int id){return  instance.repository.queryByRecipe(id);}

    public static Call<Step> post(Step step){return instance.repository.post(step);}

    public static Call<Void> put(Step step){return instance.repository.put(step);}

    public static Call<Void> delete(int id){return  instance.repository.delete(id);}

}
