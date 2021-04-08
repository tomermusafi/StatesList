package com.musafi.stateslist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.musafi.stateslist.API.entity.StateEntity;
import com.musafi.stateslist.activity.CallBack_Activity;
import com.musafi.stateslist.R;
import com.musafi.stateslist.adapter.State_Adapter;

import java.util.List;

public class BordersFragment extends Fragment {
    private View view;
    private RecyclerView state_list_fragment_RV_states;
    private Context context;
    private State_Adapter state_adapter;

    private List<StateEntity> states_list;

    private CallBack_Activity call_back;

    public BordersFragment(Context context){
        this.context = context;
        this.states_list= states_list;
    }
    public void setBorders(List<StateEntity> states_list){
        this.states_list= states_list;
    }

    public void setCall_Back(CallBack_Activity call_back){
        this.call_back = call_back;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        Log.d("pttt", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView");
        if(view==null){
            view = inflater.inflate(R.layout.borders_list_fragment, container, false);
        }

        findViews(view);
        state_adapter = new State_Adapter(context, states_list);
        initLessons();

        state_adapter.SetOnItemClickListener(new State_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, final StateEntity state) {
                Toast.makeText(context,state.getName(), Toast.LENGTH_SHORT).show();


            }

        });

        return view;
    }

    private void initLessons() {
        initList(context,state_adapter,state_list_fragment_RV_states,1);
    }


    private void findViews(View view) {
        state_list_fragment_RV_states = view.findViewById(R.id.borders_list_fragment_RV_states);

    }

    private  void initList(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, RecyclerView recyclerView, int orientation) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,orientation,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

}
