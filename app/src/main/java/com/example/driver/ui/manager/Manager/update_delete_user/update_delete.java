package com.example.driver.ui.manager.Manager.update_delete_user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.Driver;
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.Police;
import com.example.driver.R;
import com.example.driver.VioAdapter;
import com.example.driver.VioClass;
import com.example.driver.ui.driver.SlideshowViewModel;
import com.example.driver.ui.police.VioType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.driver.ui.manager.Manager.add_dreiver.DashboardFragment.TAG;

public class update_delete extends Fragment {

    String Message;
    Button search, btn_update,btn_delete;
    private File f;
    private SharedPreferences sharedPreferences;
    int driverID;
    int policeID;
    EditText car_number,userName,Name, password, phoneNo, Job,gender,carType,carNo, cardDate, licence, address,dgree;

//    EditText Name , phone, type, job,card_date, licence,address, dgree ,car_number;
    private RequestQueue queue;
    RecyclerView recyclerView;
    APIService apiService;
    List<Driver> driverList;
    Driver driver;
    Police police;
    List<Police> policeList;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.update_delete_user, container, false);
        queue = Volley.newRequestQueue(getContext());

        Identify(root);

        recyclerView = root.findViewById(R.id.update_driver_police_re);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);



        f = new File("/data/data/" + getContext().getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            driverID = sharedPreferences.getInt("ID", 0);
            Toast.makeText(getContext(), String.valueOf(driverID), Toast.LENGTH_SHORT).show();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDriverData();
                getPoliceData();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDriver();
                UpdatePolice();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDriver();
                DeletePolice();
            }
        });


        return root;

    }




    public void Identify (View root)    {

        car_number= root.findViewById(R.id.ed_txt_search);
        search=root.findViewById(R.id.btn_search);
        userName = root.findViewById(R.id.ed_txt_user_name_up);
        Name = root.findViewById(R.id.ed_txt_name_up);
        password = root.findViewById(R.id.ed_txt_user_password_up);
        phoneNo = root.findViewById(R.id.ed_txt_user_phone_up);
        Job = root.findViewById(R.id.ed_txt_user_job_up);
        gender = root.findViewById(R.id.ed_txt_user_gender_up);
        carType = root.findViewById(R.id.ed_txt_user_type_car_up);
        carNo = root.findViewById(R.id.ed_txt_user_car_number_up);
        cardDate = root.findViewById(R.id.ed_txt_user_card_date_up);
        licence = root.findViewById(R.id.ed_txt_user_licence_up);
        address = root.findViewById(R.id.ed_txt_user_address_up);
        btn_update= root.findViewById(R.id.btn_update);
        btn_delete= root.findViewById(R.id.btn_delete);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        // date = root.findViewById(R.id.txt_user_date_up);
        //address = root.findViewById(R.id.txt_address);
       // carNumber = root.findViewById(R.id.txt_user_car_Num_up);
        //Type = root.findViewById(R.id.txt_user_type_car_up);
        //userJob = root.findViewById(R.id.text_job);
      //  radioGroup = root.findViewById(R.id.rg_gender);

    }




    public void getDriverData() {

        String num = car_number.getText().toString();
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
                                driver.setJob(js.getString("JOB"));
                                driver.setGander(js.getString("GENDER"));
                                driver.setCarType(js.getString("CAR_TYPE"));
                                driver.setCarNumber(js.getString("CAR_NUM"));
                                driver.setLicence(js.getString("LICENCE"));
                                driver.setAddress(js.getString("ADDRESS"));

                                driverList.add(driver);
                            }
                            if (driverList.size() == 0) {
                                Toast.makeText(getContext(), "not found",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                carType.setText("car type: " + driver.getCarType());
                                Name.setText("Driver Name: " + driver.getName());
                                phoneNo.setText("Driver No: " + driver.getPhoneNo());
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



    public void UpdateDriver() {
        final String url = "https://driverchecker.000webhostapp.com/update_driver.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                userName.setText("");
                Name.setText("");
                password.setText("");
                phoneNo.setText("");
                Job.setText("");
                gender.setText("");
                carType.setText("");
                carNo.setText("");
                cardDate.setText("");
                licence.setText("");
                address.setText("");
                Toast.makeText(getContext(), "Update done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams() {
//            EditText car_number,userName,Name, password, phoneNo, Job,gender,carType,carNo, cardDate, licence, address,dgree;


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", userName.getText().toString());
                map.put("name", Name.getText().toString() + " " + Name.getText().toString());
                map.put("password", password.getText().toString());
                map.put("phone", phoneNo.getText().toString());
                map.put("job", Job.getText().toString());
                map.put("gender", gender.getText().toString());
                map.put("car_type", carType.getText().toString());
                map.put("car_no", carNo.getText().toString());
                map.put("card_date", cardDate.getText().toString());
                map.put("licence", licence.getText().toString());
                map.put("address", address.getText().toString());
                return map;
            }


        };

        queue.add(request);


    }

    public void DeleteDriver() {
        final String url = "https://driverchecker.000webhostapp.com/delete_driver.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                userName.setText("");
                Name.setText("");
                password.setText("");
                phoneNo.setText("");
                Job.setText("");
                gender.setText("");
                carType.setText("");
                carNo.setText("");
                cardDate.setText("");
                licence.setText("");
                address.setText("");
                Toast.makeText(getContext(), "Delete done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams()  {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", userName.getText().toString());
                map.put("name", Name.getText().toString() + " " + Name.getText().toString());
                map.put("password", password.getText().toString());
                map.put("phone", phoneNo.getText().toString());
                map.put("job", Job.getText().toString());
                map.put("gender", gender.getText().toString());
                map.put("car_type", carType.getText().toString());
                map.put("car_no", carNo.getText().toString());
                map.put("card_date", cardDate.getText().toString());
                map.put("licence", licence.getText().toString());
                map.put("address", address.getText().toString());

                return map;
            }


        };

        queue.add(request);


    }


    public void getPoliceData() {

        String num = car_number.getText().toString();
        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/deep_search.php?num=" + num + "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //JSONArray departmentArrayJson = response.getJSONArray("department");
                        try {

                            policeList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject js = response.getJSONObject(i);
                                police = new Police();
                                police.setId(js.getInt("ID"));
                                police.setUserName(js.getString("USER_NAME"));
                                police.setPassword(js.getString("PASSWORD"));
                                police.setPhoneNo(js.getString("PHONE"));
                                police.setDgree(js.getString("DGREE"));
                                police.setJob(js.getString("JOB"));
                                police.setAddress(js.getString("ADDRESS"));

                                policeList.add(police);
                            }
                            if (policeList.size() == 0) {
                                Toast.makeText(getContext(), "not found",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                address.setText("car type: " + police.getAddress());
                                userName.setText("Driver Name: " + police.getName());
                                phoneNo.setText("Driver No: " + police.getPhoneNo());
                                policeID = police.getId();


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

    public void UpdatePolice() {
        final String url = "https://driverchecker.000webhostapp.com/update_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                userName.setText("");
                Name.setText("");
                password.setText("");
                phoneNo.setText("");
                Job.setText("");
                gender.setText("");
                address.setText("");
                Toast.makeText(getContext(), "Update done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams() {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", userName.getText().toString());
                map.put("name", Name.getText().toString() + " " + Name.getText().toString());
                map.put("password", password.getText().toString());
                map.put("phone", phoneNo.getText().toString());
                map.put("dgree", Job.getText().toString());
                map.put("jobID", gender.getText().toString());
                map.put("address", address.getText().toString());


                return map;
            }


        };

        queue.add(request);


    }

    public void DeletePolice() {
        final String url = "https://driverchecker.000webhostapp.com/delete_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                userName.setText("");
                Name.setText("");
                password.setText("");
                phoneNo.setText("");
                Job.setText("");
                gender.setText("");
                address.setText("");
                Toast.makeText(getContext(), "Update done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams()  {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", userName.getText().toString());
                map.put("name", Name.getText().toString() + " " + Name.getText().toString());
                map.put("password", password.getText().toString());
                map.put("phone", phoneNo.getText().toString());
                map.put("dgree", Job.getText().toString());
                map.put("jobID", gender.getText().toString());
                map.put("address", address.getText().toString());


                return map;
            }


        };

        queue.add(request);

    }





    Handler handler = handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Log.i(TAG, "Handler " + msg.what);
            if (msg.what == 1) {
                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Upload Field", Toast.LENGTH_SHORT).show();
            }
        }

    };
}

