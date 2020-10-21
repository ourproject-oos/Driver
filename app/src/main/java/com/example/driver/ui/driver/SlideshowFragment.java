package com.example.driver.ui.driver;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;
import com.example.driver.R;
import com.example.driver.VioAdapter;
import com.example.driver.VioClass;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SlideshowFragment extends Fragment {

    String Message;
    private SlideshowViewModel slideshowViewModel;
    private File f;
    private SharedPreferences sharedPreferences;
    int driverID;
    VioAdapter vioAdapter;
    TextView name , cardNo , licence_date,type,date,location,card_number;
    List<VioClass> vioClassList = new ArrayList<>();
    Navigation navigation;
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

        Identify(root);

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


    public void Identify (View root)
    {
        name = root.findViewById(R.id.text_driver_Name);
        cardNo = root.findViewById(R.id.text_driver_car_number);
        licence_date = root.findViewById(R.id.text_driver_licence);
        type = root.findViewById(R.id.text_driver_Type);
        date = root.findViewById(R.id.text_driver_date);
        card_number = root.findViewById(R.id.text_driver_card_number);
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
/*"NAME": "yousra nabil",
        "PHONE": 725745789,
        "PASSWORD": 1234,
        "USER_NAME": "yousra",
        "CREATED_DATE": "2020-02-23 11:14:00",
        "CAR_NUM": 123456,
        "CAR_TYPE": 25806,
        "JOB": "business",
        "ADDRESS": "sanaa",
        "GENDER": "female",
        "DRIVER_ID": 3,
        "POLICE_ID": 0,
        "AMOUNT": 5000,
        "TYPE": "accident",
        "DATE": "2020-02-23 23:06:11",
        "LAT": 0,
        "LANG": 0*/


                                vioClass = new VioClass();
                                vioClass.setId(js.getInt("ID"));
                                vioClass.setDriverId(js.getInt("DRIVER_ID"));
                                vioClass.setName(js.getString("USER_NAME"));
                                vioClass.setCarNumber(js.getString("CAR_NUM"));
 //                             vioClass.setLicence(js.getString("LICENCE"));
                                vioClass.setType(js.getString("TYPE"));
                                vioClass.setDate(js.getString("DATE"));
                                vioClass.setAddress(js.getString("ADDRESS"));
                                vioClass.setCardDate(js.getString("CREATED_DATE"));
                                vioClass.setLatLocation(js.getDouble("LAT"));
                                vioClass.setLongLocation(js.getDouble("LANG"));
                                vioClass.setAmount(js.getString("AMOUNT"));


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

//    public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//
//        public void OnMessageReceived(@NonNull RemoteMessage remoteMessage) {
//            super.onMessageReceived(remoteMessage);
//
//            Message = remoteMessage.getData().get("Message");
//
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentText(Message);
//
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            manager.notify(0, builder.build());
//        }
//
//
//    }
}