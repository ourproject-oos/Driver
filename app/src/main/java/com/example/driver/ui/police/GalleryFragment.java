package com.example.driver.ui.police;

import android.Manifest;
import android.annotation.SuppressLint;
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
import retrofit2.Call;
import retrofit2.Callback;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;;
import com.android.volley.toolbox.Volley;
import com.example.driver.Driver;
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.Notifications.Data;
import com.example.driver.Notifications.MyResponse;
import com.example.driver.Notifications.NotificationSender;
import com.example.driver.Notifications.Token;
import com.example.driver.NukeSSLCerts;
import com.example.driver.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
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
import static com.example.driver.ui.manager.Manager.add_dreiver.DashboardFragment.TAG;

public class GalleryFragment extends Fragment {

    private static final int MY_LOCATION_REQUEST_CODE = 44;
    private GalleryViewModel galleryViewModel;
    EditText txtCarNo, txtCarNumber;
    CheckBox chVolType1, chVolType2, chVolType3, chVolType4, chVolType5, chVolType6, chVolType7, chVolType8, chVolType9, chVolType10, chVolType11, chVolType12, chVolType13, chVolType14, chVolType15;
    Button addBtn, searchBtn;
    ImageButton addLocationBtn, imageBtn;
    TextView txtName, txtCarType, txtDriverNo;
    int driverID, policeID;
    Driver driver;
    RecyclerView rvTypeVio;
    List<Driver> driverList;
    private RequestQueue queue;
    private File f;
    private APIService apiService;
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
//      apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


        setVioTyp();



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

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getlocation();
////                getUserData();
//            }
//        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setViolationData();

//                FirebaseDatabase.getInstance().getReference().child("Tokens").child(txtCarNumber.getText().toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String usertoken=dataSnapshot.getValue(String.class);
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Driving_against_the_flow_of_traffic));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Impending_traffic));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_no_parking));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_high_speed));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Driving_without_valid_documentation));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Driving_without_drivers_license));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_driving_vehicle_that_produces_excessive_smoke));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Driving_through_or_stopping_in_crossing_zone));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_car_with_no_number));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_not_wearing_seatbelt));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Reckless_driving));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_unnecessary_usage_of_the_horn));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Refusing_or_comply_to_policemen_signal));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_Unsafe_overtake));
//                        sendNotifications(usertoken,getString(R.string.vio_typ_using_cell_phone_while_driving));
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


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
                , getString(R.string.vio_typ_no_parking), getString(R.string.vio_typ_high_speed)
                , getString(R.string.vio_typ_Driving_without_valid_documentation), getString(R.string.vio_typ_Driving_without_drivers_license)
                , getString(R.string.vio_typ_driving_vehicle_that_produces_excessive_smoke), getString(R.string.vio_typ_Driving_through_or_stopping_in_crossing_zone)
                , getString(R.string.vio_typ_car_with_no_number), getString(R.string.vio_typ_not_wearing_seatbelt)
                , getString(R.string.vio_typ_Reckless_driving), getString(R.string.vio_typ_unnecessary_usage_of_the_horn)
                , getString(R.string.vio_typ_Refusing_or_comply_to_policemen_signal), getString(R.string.vio_typ_Unsafe_overtake)
                , getString(R.string.vio_typ_using_cell_phone_while_driving)};

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

//    public void getUserData() {
//
//        String num = txtCarNo.getText().toString();
//        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/deep_search.php?num=" + num + "",
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //JSONArray departmentArrayJson = response.getJSONArray("department");
//                        try {
//
//                            driverList = new ArrayList<>();
//
//                            for (int i = 0; i < response.length(); i++) {
//
//
//                                JSONObject js = response.getJSONObject(i);
//                                driver = new Driver();
//                                driver.setId(js.getInt("ID"));
//                                driver.setUserName(js.getString("USER_NAME"));
//                                driver.setName(js.getString("USER_NAME"));
//                                driver.setPassword(js.getString("PASSWORD"));
//                                driver.setPhoneNo(js.getString("PHONE"));
//                                driver.setAddress(js.getString("ADDRESS"));
//                                driver.setJob(js.getString("JOB"));
//                                driver.setCarNumber(js.getString("CAR_NUM"));
//                                driver.setCarType(js.getString("CAR_TYPE"));
//                                driver.setGander(js.getString("GENDER"));
//
//                                driverList.add(driver);
//                            }
//                            if (driverList.size() == 0) {
//                                Toast.makeText(getContext(), "not found",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                txtCarType.setText("car type: " + driver.getCarType());
//                                txtName.setText("Driver Name: " + driver.getName());
//                                txtDriverNo.setText("Driver No: " + driver.getPhoneNo());
//                                driverID = driver.getId();
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(), "driver not found",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "driver not found",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        queue.add(jsArray);
//    }

    public void setViolationData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_v.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "" + lat + " " + langLocation, Toast.LENGTH_SHORT).show();

                txtCarNo.setText("");
//                txtCarType.setText("");
//                txtDriverNo.setText("");
//                txtName.setText("");

                Toast.makeText(getContext(), "insert done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("voly", error.getMessage());
            }

        }) {
            protected Map<String, String> getParams()  {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("d_id", String.valueOf(driver.getId()));
                map.put("p_id", String.valueOf(idPolice));
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
 //  public void notification(){

//       FirebaseMessaging.getInstance().subscribeToTopic("voi")
//               .addOnCompleteListener(new OnCompleteListener<Void>() {
//                   @Override
//                   public void onComplete(@NonNull Task<Void> task) {
//                       String msg = getString(R.string.msg_subscribed);
//                       if (!task.isSuccessful()) {
//                           msg = getString(R.string.msg_subscribe_failed);
//                       }
//                       Log.d(TAG, msg);
//                       Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//                   }
//
//               });


  // }
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

            if (requestCode == 555 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                checkAndroidVersion();
            }
        }



    }


    private void UpdateToken()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Token").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void sendNotifications(String usertoken,String message)
    {
        Data data = new Data(message);
        NotificationSender sender = new NotificationSender(data,usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {

                if (response.code()==200)
                {
                    if (response.body().success != 1)
                    {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }
}