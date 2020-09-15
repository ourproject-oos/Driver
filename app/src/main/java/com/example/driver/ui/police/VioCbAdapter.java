package com.example.driver.ui.police;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;
import com.example.driver.VioAdapter;

import java.util.List;

class VioCbAdapter extends RecyclerView.Adapter<VioCbAdapter.VhVioCb> {

    List<VioType> vhVioCbs;
    Context context;

    public VioCbAdapter(List<VioType> vhVioCbs, Context context) {
        this.vhVioCbs = vhVioCbs;
        this.context = context;
    }

    @NonNull
    @Override
    public VhVioCb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VhVioCb vh;


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.typ_vio_item, parent, false);
        vh = new VhVioCb(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull VhVioCb holder, int position) {
        VioType vioType = vhVioCbs.get(position);

    }

    @Override
    public int getItemCount() {
        if (vhVioCbs != null)
            return vhVioCbs.size();
        else
            return 0;
    }

    public class VhVioCb extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public VhVioCb(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_item_vio_typ);

        }
    }
}
