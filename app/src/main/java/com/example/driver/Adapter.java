package com.example.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<Contact> list;
    Context c;

    public Adapter(ArrayList<Contact> list, Context c) {
        this.list = list;
        this.c = c;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, final int position) {

        final String name = list.get(position).getName();
        final String type_of_car = list.get(position).getType_of_car();
        final int image = list.get(position).getImage();
        final int user_no = list.get(position).getUser_no();
        final int card_no = list.get(position).getCard_no();
        final int card_date = list.get(position).getCard_date();
        final int license_no = list.get(position).getLicense_no();
        final int license_date = list.get(position).getLicense_date();

        holder.name.setText(name);
        holder.type_of_car.setText(type_of_car);
        holder.imageview.setImageResource(image);
        holder.user_no.setText(user_no +"");
        holder.card_no.setText(card_no);
        holder.card_date.setText(card_date);
        holder.license_no.setText(license_no);
        holder.license_date.setText(license_date);
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.imageview.setImageResource(image);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,type_of_car,card_no,card_date,license_no,license_date,user_no;
        CardView cardView;
        ImageView imageview;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            type_of_car = itemView.findViewById(R.id.Type_Of_Car);
            card_no = itemView.findViewById(R.id.Card_NO);
            card_date = itemView.findViewById(R.id.Card_Date);
            license_no = itemView.findViewById(R.id.License_No);
            license_date = itemView.findViewById(R.id.License_Date);
            user_no = itemView.findViewById(R.id.User_NO);
            imageview=itemView.findViewById(R.id.image);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}
