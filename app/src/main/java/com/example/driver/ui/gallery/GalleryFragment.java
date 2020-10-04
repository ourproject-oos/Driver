package com.example.driver.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.Driver;
import com.example.driver.NukeSSLCerts;
import com.example.driver.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    EditText txtCarNo;
    Button addBtn, searchBtn;
    ImageButton addLocationBtn;
    TextView txtName, txtCarType, txtDriverNo;
    int driverID, policeID;
    Driver driver;
    List<Driver> driverList;
    private RequestQueue queue;
    private File f;
    private SharedPreferences sharedPreferences;
    int idPolice;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    private double lat,  langLocation;

    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        addLocationBtn=root.findViewById(R.id.btn_add_place_location);
        addBtn = root.findViewById(R.id.add_v_btn);

        f = new File("/data/data/" + getContext().getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            idPolice = sharedPreferences.getInt("ID", 0);
            Toast.makeText(getContext(), String.valueOf(idPolice), Toast.LENGTH_SHORT).show();
        }

        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(getContext());
        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext().getApplicationContext();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserData();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

setViolationData();
            }
        });
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    public void getUserData() {

        String num = txtCarNo.getText().toString();
        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/deep_search.php?num=" + num + "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //JSONArray departmentArrayJson = response.getJSONArray("department");
                        try {

                            driverList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject js = response.getJSONObject(i);
                                driver = new Driver();
                                driver.setId(js.getInt("ID"));
                                driver.setUserName(js.getString("USER_NAME"));
                                driver.setName(js.getString("USER_NAME"));
                                driver.setPassword(js.getString("PASSWORD"));
                                driver.setPhoneNo(js.getString("PHONE"));
                                driver.setAddress(js.getString("ADDRESS"));
                                driver.setJob(js.getString("JOB"));
                                driver.setCarNumber(js.getString("CAR_NUM"));
                                driver.setCarType(js.getString("CAR_TYPE"));
                                driver.setGander(js.getString("GENDER"));


                                driverList.add(driver);
                            }
                            if (driverList.size() == 0) {
                                Toast.makeText(getContext(), "not found",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                txtCarType.setText("car type: " + driver.getCarType());
                                txtName.setText("Driver Name: " + driver.getName());
                                txtDriverNo.setText("Driver No: " + driver.getPhoneNo());
                                driverID = driver.getId();


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

    public void setViolationData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                txtCarNo.setText("");
                txtCarType.setText("");
               txtDriverNo.setText("");
                txtName.setText("");

                Toast.makeText(getContext(), "insert done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("d_id", String.valueOf(driver.getId()));
                map.put("p_id", String.valueOf(policeID));
               map.put("lat", String.valueOf(lat));
               map.put("lang", String.valueOf(langLocation));
                return map;
            }


        };

        queue.add(request);


    }

    int PLACE_PICKER_REQUEST = 1;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace( getActivity(),data);
                String toastMsg = String.format("Place: %s", place.getName());
                lat = place.getLatLng().latitude;
                langLocation = place.getLatLng().longitude;
                Toast.makeText(getContext(), String.valueOf(lat), Toast.LENGTH_SHORT).show();

            }
        }
    }



}