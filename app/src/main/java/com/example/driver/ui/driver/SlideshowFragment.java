package com.example.driver.ui.driver;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.R;
import com.example.driver.VioAdapter;
import com.example.driver.VioClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private File f;
    private SharedPreferences sharedPreferences;
    int driverID;
    VioAdapter vioAdapter;

    List<VioClass> vioClassList = new ArrayList<>();

    VioClass vioClass;
    private RequestQueue queue;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        queue = Volley.newRequestQueue(getContext());
        recyclerView = root.findViewById(R.id.recycle_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        f = new File("/data/data/" + getContext().getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            driverID = sharedPreferences.getInt("ID", 0);
            Toast.makeText(getContext(), String.valueOf(driverID), Toast.LENGTH_SHORT).show();
        }


        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        getUserData();
        return root;
    }


    public void getUserData() {


        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/fetch_v.php?d_id=" + driverID + "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //JSONArray departmentArrayJson = response.getJSONArray("department");
                        try {

                            vioClassList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject js = response.getJSONObject(i);

                                vioClass = new VioClass();
                                vioClass.setId(js.getInt("ID"));
                                vioClass.setName(js.getString("NAME"));
                                vioClass.setDate(js.getString("DATE"));
//                                vioClass.setType(js.getString("TYPE"));
                                vioClass.setLatLocation(js.getDouble("LAT"));
                                vioClass.setLongLocation(js.getDouble("LANG"));
                                vioClass.setCarNumber(js.getString("CAR1_NUM"));
                                vioClassList.add(vioClass);
                            }
                            if (vioClassList.size() == 0) {
                                Toast.makeText(getContext(), "not found",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                vioAdapter = new VioAdapter(getContext(), vioClassList);
                                vioAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(vioAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "driver not found",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "driver not found",
                        Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsArray);
    }

}