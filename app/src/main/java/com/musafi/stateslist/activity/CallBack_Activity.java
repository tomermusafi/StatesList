package com.musafi.stateslist.activity;

import com.musafi.stateslist.API.entity.StateEntity;

import java.util.List;

public interface CallBack_Activity {
     void goToStateList();
     void goToBordersList(List<StateEntity> borders, String name);
}
