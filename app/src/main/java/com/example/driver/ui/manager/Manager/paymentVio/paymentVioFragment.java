package com.example.driver.ui.manager.Manager.paymentVio;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDB;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDao;
import com.example.driver.Driver;
import com.example.driver.LoadingDialog;
import com.example.driver.Manager;
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.NukeSSLCerts;
import com.example.driver.Police;
import com.example.driver.R;
import com.example.driver.VioAdapter;
import com.example.driver.VioClass;
import com.example.driver.VolleyMultipartRequest;
import com.example.driver.VolleySingleton;
import com.example.driver.ui.manager.Manager.add_dreiver.DashboardViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

//import com.example.driver.ui.UploadImageApacheHttp;

public class paymentVioFragment extends Fragment {
    public static final String TAG = "Upload Image";
    LoadingDialog loadingDialog;
    public static final String UPLOAD_URL = "https://driverchecker.000webhostapp.com/insert_driver.php";
    public static final String UPLOAD_KEY = "upload_image";

    private int PICK_IMAGE_REQUEST = 100;
    private ImageView imgView;
    private Bitmap bitmap, bitmap1;
    private Uri filePath;
    private Object selectedFilePath;
    private APIService apiService;
    private DashboardViewModel dashboardViewModel;
    TextInputLayout userName, password, rePassword, firstName, lastName, phoneNo, email, userJob, carNumber, cardDate, licence, carType, address;
    TextInputEditText edt_userName, edt_password, edt_rePassword, edt_firstName, edt_lastName, edt_phoneNo, edt_email, edt_userJob, edt_carNumber, edt_cardDate, edt_licence, edt_carType, edt_address;
    Button btn_SignUp;
    ImageView imageView;
    JSONObject jsonObject;
    RequestQueue queue;
    private RadioGroup radioGroup;
    private String gender = "male";
    ArrayList<Manager> list;
    ManagerDB manager;
    ManagerDao managerDao;
    Driver driver;
    Police police;
    Uri url;
    Uri imageUri;
    byte[] byteArray = null;
    private String mediaPath = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    RecyclerView rvPaymentVio;
    private ArrayList<VioClass> vioClassList;

    vioPaymentAdapter vioAdapter;
    VioClass vioClass;

    // private static final int PICK_IMAGE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_payment_vio, container, false);
        rvPaymentVio = root.findViewById(R.id.rv_vio_payment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvPaymentVio.setLayoutManager(linearLayoutManager);


        IdentifyMethod(root);
        return root;
    }

    private void IdentifyMethod(View root) {

    }

    public void getUserData() {


        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/fetch_v.php",
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
                                vioClass.setPaid(js.getBoolean("IS_PAID"));


                                vioClassList.add(vioClass);
                            }
                            if (vioClassList.size() == 0) {
                                Toast.makeText(getContext(), "not found",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                vioAdapter = new vioPaymentAdapter(getContext(), vioClassList);
                                vioAdapter.notifyDataSetChanged();
                                rvPaymentVio.setAdapter(vioAdapter);
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


//
//    private void UpdateToken()
//    {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken = FirebaseInstanceId.getInstance().getToken();
//        Token token = new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Token").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
//    }
//
//    public void sendNotifications(String usertoken,String message)
//    {
//        Data data = new Data(message);
//        NotificationSender sender = new NotificationSender(data,usertoken);
//        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
//
//                if (response.code()==200)
//                {
//                    if (response.body().success != 1)
//                    {
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

}

//    public void setter() {
//        manager.getUserName(userName.getText().toString().trim());
//        manager.setPassword(Integer.parseInt(password.getText().toString().trim()));
//        manager.setRePassword(Integer.parseInt(rePassword.getText().toString().trim()));
//        manager.setDriverFirstName(firstName.getText().toString().trim());
//        manager.setDriverLastName(lastName.getText().toString().trim());
//        manager.setPhone(Integer.parseInt(phoneNo.getText().toString().trim()));
//        manager.setAdress(address.getText().toString().trim());
//        manager.setCarNumber(Integer.parseInt(carNumber.getText().toString().trim()));
//        manager.setCarType(carType.getText().toString().trim());
//        manager.setUserJob(userJob.getText().toString().trim());
//    }
//
//
//    private void addDriver() {
//        btn_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setter();
//                managerDao.insertManager(manager);
//
//                Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
