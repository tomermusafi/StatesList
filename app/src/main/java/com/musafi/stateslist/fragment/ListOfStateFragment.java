package com.musafi.stateslist.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musafi.stateslist.API.entity.StateEntity;
import com.musafi.stateslist.activity.CallBack_Activity;
import com.musafi.stateslist.R;
import com.musafi.stateslist.adapter.State_Adapter;

import java.util.ArrayList;
import java.util.List;

public class ListOfStateFragment extends Fragment {
    private View view;
    private RecyclerView state_list_fragment_RV_states;
    private Context context;
    private State_Adapter state_adapter;

    private List<StateEntity> states_list;
    private List<StateEntity> all_states_list;

    private CallBack_Activity call_back;

    public ListOfStateFragment(Context context, List<StateEntity> states_list){
        this.context = context;
        this.states_list= states_list;
        this.all_states_list = new ArrayList<>(states_list);
    }

    public void setCall_Back(CallBack_Activity call_back){
        this.call_back = call_back;
    }
    public void setStates(List<StateEntity> states_list){
        this.states_list= new ArrayList<>(states_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        Log.d("pttt", "onCreate");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView");
        if(view==null){
            view = inflater.inflate(R.layout.state_list_fragment, container, false);
        }

        findViews(view);
        state_adapter = new State_Adapter(context, states_list);
        initLessons();


        state_adapter.SetOnItemClickListener(new State_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, final StateEntity state) {
                List<StateEntity> borders = findBorders(state.getBorders());
                if(borders.size() == 0){
                    Toast.makeText(context, "No borders for " + state.getName(), Toast.LENGTH_LONG).show();
                }else{
                    hideSoftKeyboard((Activity) context);
                    call_back.goToBordersList(borders,state.getName());
                }

            }

        });

        return view;
    }

    private void initLessons() {
        initList(context,state_adapter,state_list_fragment_RV_states,1);
    }


    private void findViews(View view) {
        state_list_fragment_RV_states = view.findViewById(R.id.state_list_fragment_RV_states);

    }

    private  void initList(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, RecyclerView recyclerView, int orientation) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,orientation,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private List<StateEntity> findBorders(String [] borders){
        List<StateEntity> borders_states= new ArrayList<>();
        for (String border : borders) {
            for (StateEntity state : all_states_list) {
                if (state.getAlpha3Code().equals(border)) {
                    borders_states.add(state);
                    break;
                }
            }
        }
        return borders_states;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                state_adapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }


}
