package com.example.driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Recycle_Fragment extends Fragment {

    public static Fragment newInstance() {
        return new Recycle_Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        List<String> list = new ArrayList<>();
        list.add("ONE");
        list.add("TWO");
        list.add("THREE");
        list.add("FOUR");
        list.add("FIVE");
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(list));

        return view;

    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private CardView card_View;
        private TextView textView,textView1;
        public RecyclerViewHolder(View itemView){
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container)
        {
            super(inflater.inflate(R.layout.card_view, container , false));

            card_View = itemView.findViewById(R.id.card_view);
            textView = itemView.findViewById(R.id.violation);
            textView1 = itemView.findViewById(R.id.price);
        }
    }
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>
    {
        private List<String> mlist;
        public RecyclerViewAdapter(List<String> list)
        {
            this.mlist = list;
        }
        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

            holder.textView.setText(mlist.get(position));

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }

}
