package com.musafi.stateslist.API.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musafi.stateslist.API.StateAPI;
import com.musafi.stateslist.API.entity.StateEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateController {

    static final String BASE_URL = "https://restcountries.eu/rest/v2/";

    private CallBack_States callBack_states;

    Callback<List<StateEntity>> statesArrayCallBack = new Callback<List<StateEntity>>() {
        @Override
        public void onResponse(Call<List<StateEntity>> call, Response<List<StateEntity>> response) {
            if (response.isSuccessful()) {
                List<StateEntity> states = response.body();

                if (callBack_states != null) {
                    callBack_states.states(states);
                }
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<List<StateEntity>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public void fetchAllStates(CallBack_States callBack_states) {
        this.callBack_states = callBack_states;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        StateAPI statesAPI = retrofit.create(StateAPI.class);

        Call<List<StateEntity>> call = statesAPI.getAllStates();
        call.enqueue(statesArrayCallBack);
    }


    public interface CallBack_States {
        void states(List<StateEntity> states);
    }

}
