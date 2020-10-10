package com.example.driver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class VioAdapter extends RecyclerView.Adapter {

    Context context;
    List<VioClass> vioClassList;

    public VioAdapter(Context context, List<VioClass> vioClassList) {
        this.context = context;
        this.vioClassList = vioClassList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vio, parent, false);
        vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final VioClass vioClass = vioClassList.get(position);
        ((ViewHolder) holder).txtName.setText("Police name: " + vioClass.getName());
        ((ViewHolder) holder).txtCarNumber.setText("car Num: " + vioClass.getCarNumber());
        ((ViewHolder) holder).licence.setText("car Num: " + vioClass.getLicence());
        ((ViewHolder) holder).txtType.setText("Vio Type: " + vioClass.getType());
        ((ViewHolder) holder).txtDate.setText("Vio Date: " + vioClass.getDate());


        ((ViewHolder) holder).goLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String query = "?q=" + vioClass.getLatLocation() + "," + vioClass.getLongLocation() + "(" + vioClass.getName() + " car number: " + vioClass.getCarNumber() + ")" + "&z=18";
//                String encodedQuery = Uri.encode(query);
//
//                Uri gmmIntentUri2 = Uri.parse("geo:" + vioClass.getLatLocation() + "," +
//                        vioClass.getLongLocation() + query);
//                Intent mapIntent2 = new Intent(Intent.ACTION_VIEW,
//                        gmmIntentUri2);
//                mapIntent2.setPackage("com.google.android.apps.maps");
//                context.startActivity(mapIntent2);

                MapsActivity mapsActivity = new MapsActivity();
                mapsActivity.setLatLocation(vioClass.latLocation);
                mapsActivity.setLongLocation(vioClass.longLocation);
                //



            }
        });


    }

    @Override
    public int getItemCount() {

        return vioClassList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, licence;
        public TextView txtCarNumber;
        public TextView txtType;
        public TextView location;
        public TextView txtDate, cardDate;
        ImageButton goLocation;


        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textView_name);
            txtCarNumber = itemView.findViewById(R.id.textView_number_car);
            cardDate = itemView.findViewById(R.id.txt_card_date);
            txtType = itemView.findViewById(R.id.textView_type);
            txtDate = itemView.findViewById(R.id.text_driver_date);
            licence = itemView.findViewById(R.id.txt_licence);


        }

    }
}
