package com.musafi.stateslist.API;

import com.musafi.stateslist.API.entity.StateEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StateAPI {
    @GET("all?fields=name;capital;borders;nativeName;flag;alpha3Code")
    Call<List<StateEntity>> getAllStates();
}
