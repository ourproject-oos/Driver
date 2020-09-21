package com.example.driver.ui.manager.Manager.update_delete_user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.R;

import java.util.List;

class update_delete_Adapter extends RecyclerView.Adapter<update_delete_Adapter.UdateDelete> {

    List<update_delete> UdateDelete;
    Context context;

    public update_delete_Adapter(List<update_delete> UdateDelete, Context context) {
        this.UdateDelete = UdateDelete;
        this.context = context;
    }
    @NonNull
    @Override
    public UdateDelete onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UdateDelete updatedelete;

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_user, parent, false);
        updatedelete = new update_delete_Adapter.UdateDelete(v);

        return updatedelete;
    }

    @Override
    public void onBindViewHolder(@NonNull UdateDelete holder, int position) {
        update_delete update_delete = UdateDelete.get(position);
    }

    @Override
    public int getItemCount() {
        if (UdateDelete != null)
            return UdateDelete.size();
        else
            return 0;
    }

    public class UdateDelete extends RecyclerView.ViewHolder {

        TextView textView;

        public UdateDelete(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_info_user);
        }
    }
}
