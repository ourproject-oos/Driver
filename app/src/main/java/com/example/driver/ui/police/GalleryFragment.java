package com.example.driver.ui.police;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
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
    EditText txtCarNo, txtCarNumber;
    CheckBox chVolType1, chVolType2, chVolType3, chVolType4, chVolType5, chVolType6, chVolType7, chVolType8, chVolType9, chVolType10, chVolType11, chVolType12, chVolType13, chVolType14, chVolType15;
    Button addBtn, searchBtn, imageBtn;
    ImageButton addLocationBtn;
    TextView txtName, txtCarType, txtDriverNo;
    int driverID, policeID;
    Driver driver;
    RecyclerView rvTypeVio;
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
    String serialNumber;
    Intent data;
    List<VioType> vioTypeList = new ArrayList<>();

    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        addLocationBtn = root.findViewById(R.id.btn_add_place_location);
        txtCarNumber = root.findViewById(R.id.ed_txt_car_no);
        imageBtn = root.findViewById(R.id.btn_add_image);
        addBtn = root.findViewById(R.id.add_v_btn);
        rvTypeVio = root.findViewById(R.id.rv_typ_vio);


        setVioTyp();


//        chVolType1 = root.findViewById(R.id.checkbox1);
//        chVolType2 = root.findViewById(R.id.checkbox2);
//        chVolType3 = root.findViewById(R.id.checkbox3);
//        chVolType4 = root.findViewById(R.id.checkbox4);
//        chVolType5 = root.findViewById(R.id.checkbox5);
//        chVolType6 = root.findViewById(R.id.checkbox6);
//        chVolType7 = root.findViewById(R.id.checkbox7);
//        chVolType8 = root.findViewById(R.id.checkbox8);
//        chVolType9 = root.findViewById(R.id.checkbox9);
//        chVolType10 = root.findViewById(R.id.checkbox10);
//        chVolType11= root.findViewById(R.id.checkbox11);
//        chVolType12= root.findViewById(R.id.checkbox12);
//        chVolType13= root.findViewById(R.id.checkbox13);
//        chVolType14= root.findViewById(R.id.checkbox14);
//        chVolType15= root.findViewById(R.id.checkbox15);


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

    private void setVioTyp() {
        String[] s = getVioTyp();
        for (int i = 0; i < s.length; i++) {

            VioType vioType = new VioType();
            vioType.setCbPos(i + 1);
            vioType.setTxtVioTyp(s[i]);

            vioTypeList.add(vioType);

        }
        VioCbAdapter vioCbAdapter = new VioCbAdapter(vioTypeList, getContext());
        rvTypeVio.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTypeVio.setAdapter(vioCbAdapter);

    }

    public String[] getVioTyp() {

        String[] strings = {getString(R.string.vio_typ_Driving_against_the_flow_of_traffic), getString(R.string.vio_typ_Impending_traffic)
                , getString(R.string.vio_typ_Driving_against_the_flow_of_traffic)};

        return strings;


    }


    public void getlocation() {

        mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                langLocation = location.getLongitude();
                Toast.makeText(getContext(), "" + langLocation + " " + lat, Toast.LENGTH_SHORT).show();
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
                Log.d("voly", error.getMessage());
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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


    public static String locationString(final Location location) {

        return Location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " +
                Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);

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


    public class ImageCroped extends Activity {

        public void click_me(View view) {

            if (view.getId() == imageBtn.getId()) {
                checkAndroidVersion();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        public void checkAndroidVersion() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
                } catch (Exception e) {

                }
            } else {
                pickImage();
            }
        }

        public void pickImage() {
            CropImage.startPickImageActivity(this);
        }

        private void croprequest(Uri imageUri) {
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .start(this);
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Uri imageUri = CropImage.getPickImageResultUri(this, data);
                croprequest(imageUri);
            }


            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                        if (!txtRecognizer.isOperational()) {
                            txtCarNo.setText("Detector dependencies are not yet available");
                        } else {
                            // Set the bitmap taken to the frame to perform OCR Operations.
                            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                            SparseArray items = txtRecognizer.detect(frame);
                            StringBuilder strBuilder = new StringBuilder();

                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = (TextBlock) items.valueAt(i);
                                strBuilder.append(item.getValue());
                                strBuilder.append("\n");
                                for (Text line : item.getComponents()) {
                                    //extract scanned text lines here
                                    Log.v("lines", line.getValue());
                                    for (Text element : line.getComponents()) {
                                        //extract scanned text words here
                                        Log.v("element", element.getValue());
                                    }

                                }
                            }
                            txtCarNumber.setText(strBuilder.toString());
                        }
                   //     ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

            if (requestCode == 555 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                checkAndroidVersion();
            }
        }

    }
}