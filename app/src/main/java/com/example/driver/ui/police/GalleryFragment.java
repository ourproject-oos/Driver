package com.example.driver.ui.police;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.lucem.anb.characterscanner.ScannerListener;
import com.lucem.anb.characterscanner.ScannerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment {

    private static final int MY_LOCATION_REQUEST_CODE = 44;
    private GalleryViewModel galleryViewModel;
    EditText txtCarNo;
    CheckBox chVolType1 , chVolType2 , chVolType3 , chVolType4 , chVolType5 , chVolType6 , chVolType7 , chVolType8 , chVolType9 , chVolType10 , chVolType11 , chVolType12 , chVolType13 , chVolType14 , chVolType15;
    Button addBtn, searchBtn;
    ImageButton addLocationBtn;
    TextView txtName, txtCarType, txtDriverNo;
    int driverID, policeID;
    Driver driver;
    List<Driver> driverList;
    private RequestQueue queue;
    private File f;
    ArrayAdapter<String> spArray;
    private SharedPreferences sharedPreferences;
    int idPolice;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    private double lat, langLocation;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    ScannerView scannerView;
    String serialNumber;
    Intent data;
    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        addLocationBtn = root.findViewById(R.id.btn_add_place_location);
        txtCarNo = root.findViewById(R.id.deep_search_bar);
        txtCarType = root.findViewById(R.id.driver_car_type);
        txtName = root.findViewById(R.id.driver_name);
        txtDriverNo = root.findViewById(R.id.driver_number);
        addBtn = root.findViewById(R.id.add_v_btn);
        searchBtn = root.findViewById(R.id.search_btn);

        chVolType1 = root.findViewById(R.id.checkbox1);
        chVolType2 = root.findViewById(R.id.checkbox2);
        chVolType3 = root.findViewById(R.id.checkbox3);
        chVolType4 = root.findViewById(R.id.checkbox4);
        chVolType5 = root.findViewById(R.id.checkbox5);
        chVolType6 = root.findViewById(R.id.checkbox6);
        chVolType7 = root.findViewById(R.id.checkbox7);
        chVolType8 = root.findViewById(R.id.checkbox8);
        chVolType9 = root.findViewById(R.id.checkbox9);
        chVolType10 = root.findViewById(R.id.checkbox10);
        chVolType11= root.findViewById(R.id.checkbox11);
        chVolType12= root.findViewById(R.id.checkbox12);
        chVolType13= root.findViewById(R.id.checkbox13);
        chVolType14= root.findViewById(R.id.checkbox14);
        chVolType15= root.findViewById(R.id.checkbox15);
        scannerView =root.findViewById(R.id.scanner_view);






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
                getlocation();
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



    public void getlocation() {

        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                langLocation = location.getLongitude();
                Toast.makeText(getContext(), ""+langLocation+" "+lat, Toast.LENGTH_SHORT).show();
                mLocationManager.removeUpdates(this);
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, mLocationListener);

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
        final String url = "https://driverchecker.000webhostapp.com/insert_v.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "" + lat + " " + langLocation, Toast.LENGTH_SHORT).show();

                txtCarNo.setText("");
                txtCarType.setText("");
                txtDriverNo.setText("");
                txtName.setText("");

                // Toast.makeText(getContext(), "insert done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Log.d("voly",error.getMessage());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("d_id", String.valueOf(driver.getId()));
                map.put("p_id", String.valueOf(idPolice));
                map.put("ch_box1", chVolType1.getText().toString());
                map.put("ch_box2", chVolType2.getText().toString());
                map.put("ch_box3", chVolType3.getText().toString());
                map.put("ch_box4", chVolType4.getText().toString());
                map.put("ch_box5", chVolType5.getText().toString());
                map.put("ch_box6", chVolType6.getText().toString());
                map.put("ch_box7", chVolType7.getText().toString());
                map.put("ch_box8", chVolType8.getText().toString());
                map.put("ch_box9", chVolType9.getText().toString());
                map.put("ch_box10", chVolType10.getText().toString());
                map.put("ch_box11", chVolType11.getText().toString());
                map.put("ch_box12", chVolType12.getText().toString());
                map.put("ch_box13", chVolType13.getText().toString());
                map.put("ch_box14", chVolType14.getText().toString());
                map.put("ch_box15", chVolType15.getText().toString());


                map.put("lang", String.valueOf(langLocation));
                map.put("lat", String.valueOf(lat));
                return map;
            }


        };

        queue.add(request);


    }

    int PLACE_PICKER_REQUEST = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent  data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                //lat = place.getLatLng().latitude;
                //langLocation = place.getLatLng().longitude;
                // Toast.makeText(getContext(), String.valueOf(lat), Toast.LENGTH_SHORT).show();

            }
        }

            if (data != null) {

                Random random = new Random(90);
                Calendar calendar = Calendar.getInstance();
                serialNumber = calendar.getTimeInMillis() + "" + random;

                data.getStringExtra("carNumber");


            }


            super.onActivityResult(requestCode, resultCode, data);
        }
    private void scannerViewEvents() {

        scannerView.setOnDetectedListener(getActivity(), new ScannerListener() {
            @Override
            public void onDetected(String s) {
               // Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                Log.i("result_String",s);
            }

            @Override
            public void onStateChanged(String s, int i) {
                Log.d("scanner_result", s);

            }
        });


    }

    public static String locationString (final Location location){

        return Location.convert(location.getLatitude(),Location.FORMAT_DEGREES)+" "+
                Location.convert(location.getLongitude(),Location.FORMAT_DEGREES);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission was denied. Display an error message.
            }
        }
    }
}