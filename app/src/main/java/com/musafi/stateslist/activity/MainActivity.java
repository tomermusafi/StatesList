package com.musafi.stateslist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

import com.musafi.stateslist.API.controller.StateController;
import com.musafi.stateslist.API.entity.StateEntity;
import com.musafi.stateslist.R;
import com.musafi.stateslist.fragment.BordersFragment;
import com.musafi.stateslist.fragment.ListOfStateFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<StateEntity> all_states;

    private ListOfStateFragment listOfStateFragment;
    private BordersFragment bordersFragment;

    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");
        getList();

    }


    private void getList(){
        new StateController().fetchAllStates(new StateController.CallBack_States() {
            @Override
            public void states(List<StateEntity> states) {
                all_states = new ArrayList<>(states);
                listOfStateFragment = new ListOfStateFragment(MainActivity.this, all_states);
                bordersFragment = new BordersFragment(MainActivity.this);
                listOfStateFragment.setCall_Back(callBack_activity);
                bordersFragment.setCall_Back(callBack_activity);
                listOfStateFragment.setStates(all_states);
                showFragment(listOfStateFragment);

            }
        });
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_LL_fragment, fragment);
        transaction.commit();
    }
    private CallBack_Activity callBack_activity = new CallBack_Activity() {
        @Override
        public void goToStateList() {
            getSupportActionBar().setTitle("");
            showFragment(listOfStateFragment);
            page = 0;
        }

        @Override
        public void goToBordersList(List<StateEntity> borders, String name) {
            getSupportActionBar().setTitle(name+"'s borders");
            bordersFragment.setBorders(borders);
            showFragment(bordersFragment);
            page = 1;
        }

    };

    @Override
    public void onBackPressed() {
        if(page == 1){
            getSupportActionBar().setTitle("");
            listOfStateFragment.setStates(all_states);
            showFragment(listOfStateFragment);
            page = 0;
        }else{
            super.onBackPressed();
        }
    }


}