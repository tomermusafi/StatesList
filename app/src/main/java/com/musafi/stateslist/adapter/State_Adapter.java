package com.musafi.stateslist.adapter;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.musafi.stateslist.API.entity.StateEntity;
import com.musafi.stateslist.R;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class State_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    private List<StateEntity> states;
    private List<StateEntity> statesAll;
    private OnItemClickListener mItemClickListener;

    public State_Adapter(Context context, List<StateEntity> states) {
        this.context = context;
        this.states = states;
        this.statesAll = new ArrayList<>(states);
    }

    public void updateList(ArrayList<StateEntity> states) {
        this.states = states;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.state_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final StateEntity state = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.name.setText(state.getName());
            genericViewHolder.nativeName.setText(state.getNativeName());
            genericViewHolder.capital.setText(state.getCapital());

            GlideToVectorYou
                    .init()
                    .with(context)
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() {
                           Toast.makeText(context, "Load failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResourceReady() {

                        }
                    })
                    .load(Uri.parse(state.getFlag()), genericViewHolder.flag);


        }
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    private StateEntity getItem(int position) {
        return states.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<StateEntity> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(statesAll);
            }else{
                for(StateEntity stateEntity : statesAll){
                    if(stateEntity.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(stateEntity);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            states.clear();
            states.addAll((Collection<?extends StateEntity>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView name;
        private TextView nativeName;
        private TextView capital;
        private ImageView flag;


        public ViewHolder(final View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.state_list_TXT_name);
            this.nativeName = itemView.findViewById(R.id.state_list_TXT_native_name);
            this.capital = itemView.findViewById(R.id.state_list_TXT_capital);
            this.flag = itemView.findViewById(R.id.state_list_IMG_flag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                }
            });
        }
    }

    public void removeAt(int position) {
        states.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, states.size());
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, StateEntity state);
    }
}