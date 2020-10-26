package com.example.driver.ui.manager.Manager.update_delete_user;

import android.app.job.JobParameters;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.driver.Driver;
import com.example.driver.Notifications.APIService;
import com.example.driver.Police;
import com.example.driver.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.driver.ui.manager.Manager.add_dreiver.DashboardFragment.TAG;

public class update_delete extends Fragment {

    String Message, major_up;
    Button search, btn_update, btn_delete;
    private File f;
    private SharedPreferences sharedPreferences;
    int driverID;
    int policeID;
    Spinner spinner_up;
    LinearLayout lyPoliceDtl, lyDriverDtl, lyOption;
    ArrayAdapter<String> spinnerAdapter;
    EditText userNameDriver, NameDriver, passwordDriver, phoneNoDriver, JobDriver,
            genderDriver, carTypeDriver, carNoDriver, cardDateDriver, licence, addressDriver;

    EditText userNamePolice, NamePolice, passwordPolice, phoneNoPolice,
            jobIdPolice, dgreePolice, addressPolice;

    TextView tvId;

    TextInputEditText car_number;
    TextInputLayout edit_car_number;
    private RequestQueue queue;
    APIService apiService;
    List<Driver> driverList;
    Driver driver;
    Police police;
    String spinner;
    List<Police> policeList;
    String spCheckedMjr = "POLICE";
    public String driver_id,police_id;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.update_delete_user, container, false);
        queue = Volley.newRequestQueue(getContext());

        Identify(root);
        seiListener();

        spinnerAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final String[] updateList = {"POLICE", "DRIVER"};
        spinnerAdapter.addAll(updateList);
        spinner_up.setAdapter(spinnerAdapter);


        spinner_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spCheckedMjr = adapterView.getSelectedItem().toString();
                Log.i("spCheckedMjr", spCheckedMjr);
                if (spCheckedMjr.contains("DRIVER")) {

                    edit_car_number.setHint("search by Car No");
                } else {

                    edit_car_number.setHint("search by Police job No");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


        f = new File("/data/data/" + getContext().getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            driverID = sharedPreferences.getInt("ID", 0);
            Toast.makeText(getContext(), String.valueOf(driverID), Toast.LENGTH_SHORT).show();
        }


        return root;

    }

    private void seiListener() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spCheckedMjr.contains("DRIVER"))
                    DeleteDriver(driver_id);

                else
                {
                    DeletePolice(police_id);
                }

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spCheckedMjr.contains("DRIVER"))
                    UpdateDriver();

                else
                {
                    UpdatePolice();
                }

            }
        });

    }


    public void Identify(View root) {

        spinner_up = root.findViewById(R.id.Spinner_major_update);
        lyDriverDtl = root.findViewById(R.id.driver_detail);
        lyPoliceDtl = root.findViewById(R.id.police_detail);
        lyOption = root.findViewById(R.id.edit_op_layout);
        edit_car_number = root.findViewById(R.id.txt_search);
        car_number = root.findViewById(R.id.ed_txt_search);
        search = root.findViewById(R.id.btn_search);
        userNameDriver = root.findViewById(R.id.ed_txt_user_name_driver);
        NameDriver = root.findViewById(R.id.ed_txt_name_driver);
        passwordDriver = root.findViewById(R.id.ed_txt_user_password_driver);
        phoneNoDriver = root.findViewById(R.id.ed_txt_user_phone_driver);
        JobDriver = root.findViewById(R.id.ed_txt_user_job_driver);
        genderDriver = root.findViewById(R.id.ed_txt_user_gender_driver);
        carTypeDriver = root.findViewById(R.id.ed_txt_user_type_car_driver);
        carNoDriver = root.findViewById(R.id.ed_txt_user_car_number_driver);
        cardDateDriver = root.findViewById(R.id.ed_txt_user_card_date_driver);
        licence = root.findViewById(R.id.ed_txt_user_licence_driver);
        addressDriver = root.findViewById(R.id.ed_txt_user_address_driver);
        addressPolice = root.findViewById(R.id.ed_txt_user_address_police);
        userNamePolice = root.findViewById(R.id.ed_txt_user_name_police);
        passwordPolice = root.findViewById(R.id.ed_txt_user_password_police);
        jobIdPolice=root.findViewById(R.id.ed_txt_user_job_police);
        NamePolice = root.findViewById(R.id.ed_txt_name_police);
        phoneNoPolice = root.findViewById(R.id.ed_txt_user_phone_police);
        dgreePolice=root.findViewById(R.id.ed_txt_user_dgree_police);
        tvId = root.findViewById(R.id.tv_id);
        btn_update = root.findViewById(R.id.btn_update);
        btn_delete = root.findViewById(R.id.btn_delete);
//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        // date = root.findViewById(R.id.txt_user_date_up);
        //address = root.findViewById(R.id.txt_address);
        // carNumber = root.findViewById(R.id.txt_user_car_Num_up);
        //Type = root.findViewById(R.id.txt_user_type_car_up);
        //userJob = root.findViewById(R.id.text_job);
        //  radioGroup = root.findViewById(R.id.rg_gender);

    }


    public void getData() {

        String num = car_number.getText().toString();
        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/deep_search.php?num=" + num + "&major=" + spCheckedMjr + "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //JSONArray departmentArrayJson = response.getJSONArray("department");
                        try {

                            driverList = new ArrayList<>();
                            policeList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject js = response.getJSONObject(i);

                                if (spCheckedMjr.equals("DRIVER")) {

                                    driver = new Driver();
                                    driver.setId(js.getInt("ID"));
                                    driver.setUserName(js.getString("USER_NAME"));
                                    driver.setName(js.getString("NAME"));
                                    driver.setPassword(js.getString("PASSWORD"));
                                    driver.setPhoneNo(js.getString("PHONE"));
                                    driver.setJob(js.getString("JOB"));
                                    driver.setGander(js.getString("GENDER"));
                                    driver.setCarType(js.getString("CAR_TYPE"));
                                    driver.setCarNumber(js.getString("CAR_NUM"));
                                    driver.setCarNumber(js.getString("CARD_DATE"));
                                    driver.setLicence(js.getString("LICENCE"));
                                    driver.setAddress(js.getString("ADDRESS"));

                                    driverList.add(driver);

                                    if (driverList.size() != 0) {
                                        lyDriverDtl.setVisibility(View.VISIBLE);
                                        lyOption.setVisibility(View.VISIBLE);
                                        driver_id = String.valueOf(driver.getId());
                                        tvId.setText("driver id: " + driver.getId());
                                        userNameDriver.setText(driver.getUserName());
                                        NameDriver.setText(driver.getName());
                                        passwordDriver.setText(driver.getPassword());
                                        phoneNoDriver.setText(driver.getPhoneNo());
                                        JobDriver.setText(driver.getJob());
                                        genderDriver.setText(driver.getGander());
                                        carTypeDriver.setText(driver.getCarType());
                                        carNoDriver.setText(driver.getCarNumber());
                                        cardDateDriver.setText(driver.getCardDate());
                                        licence.setText(driver.getLicence());
                                        addressDriver.setText(driver.getAddress());


                                    }

                                } else {

                                    police = new Police();
                                    police.setId(js.getInt("ID"));
                                    police.setUserName(js.getString("USER_NAME"));
                                    police.setName(js.getString("NAME"));
                                    police.setPassword(js.getString("PASSWORD"));
                                    police.setPhoneNo(js.getString("PHONE"));
                                    police.setAddress(js.getString("ADDRESS"));
                                    police.setJob_id(js.getString("JOB_ID"));
                                    police.setDgree(js.getString("DGREE"));
                                    police.setLat(js.getString("LAT"));
                                    police.setLang(js.getString("LANG"));

                                    policeList.add(police);
                                }
                                if (policeList.size() != 0) {

                                    tvId.setText("police id: " + police.getId());
                                    lyPoliceDtl.setVisibility(View.VISIBLE);
                                    lyOption.setVisibility(View.VISIBLE);
                                    addressPolice.setText(police.getAddress());
                                    userNamePolice.setText(police.getUserName());
                                    NamePolice.setText(police.getJob_id());
                                    phoneNoPolice.setText(police.getPhoneNo());
                                    // dgreePolice.setText(police.getDgree());
                                    passwordPolice.setText(police.getPassword());
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "not found",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "not found",
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
                userNameDriver.setText("");
                NameDriver.setText("");
                passwordDriver.setText("");
                phoneNoDriver.setText("");
                JobDriver.setText("");
                genderDriver.setText("");
                carTypeDriver.setText("");
                carNoDriver.setText("");
                cardDateDriver.setText("");
                licence.setText("");
                addressDriver.setText("");
                Toast.makeText(getContext(), "Update Driver done", Toast.LENGTH_SHORT).show();
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
                map.put("user_name", userNameDriver.getText().toString());
                map.put("name", NameDriver.getText().toString() + " " + NameDriver.getText().toString());
                map.put("password", passwordDriver.getText().toString());
                map.put("phone", phoneNoDriver.getText().toString());
                map.put("job", JobDriver.getText().toString());
                map.put("gender", genderDriver.getText().toString());
                map.put("car_type", carTypeDriver.getText().toString());
                map.put("car_no", carNoDriver.getText().toString());
                map.put("card_date", cardDateDriver.getText().toString());
                map.put("licence", licence.getText().toString());
                map.put("address", addressDriver.getText().toString());
                return map;
            }


        };

        queue.add(request);


    }

    public void DeleteDriver(String driver_id) {
         String url = "https://driverchecker.000webhostapp.com/delete_driver.php?driver_id=" + driver_id +"";
        Log.d("url", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("url",response.toString());
                        userNameDriver.setText("");
                        NameDriver.setText("");
                        passwordDriver.setText("");
                        phoneNoDriver.setText("");
                        JobDriver.setText("");
                        genderDriver.setText("");
                        carTypeDriver.setText("");
                        carNoDriver.setText("");
                        cardDateDriver.setText("");
                        licence.setText("");
                        addressDriver.setText("");
                        Toast.makeText(getContext(), "Delete Driver done", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
error.printStackTrace();
            }

        });

        queue.add(jsonObjectRequest);


    }


//    public void getPoliceData() {
//
//        String num = car_number.getText().toString();
//        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/deep_search.php?num=" + num + "",
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        //JSONArray departmentArrayJson = response.getJSONArray("department");
//                        try {
//
//                            policeList = new ArrayList<>();
//
//                            for (int i = 0; i < response.length(); i++) {
//
//                                JSONObject js = response.getJSONObject(i);
//                                police = new Police();
//                                police.setId(js.getInt("ID"));
//                                police.setUserName(js.getString("USER_NAME"));
//                                police.setPassword(js.getString("PASSWORD"));
//                                police.setPhoneNo(js.getString("PHONE"));
//                                police.setDgree(js.getString("DGREE"));
//                                police.setJob(js.getString("JOB"));
//                                police.setAddress(js.getString("ADDRESS"));
//
//                                policeList.add(police);
//                            }
//                            if (policeList.size() == 0) {
//                                Toast.makeText(getContext(), "not found",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                addressDriver.setText("car type: " + police.getAddress());
//                                userNameDriver.setText("Driver Name: " + police.getName());
//                                phoneNoDriver.setText("Driver No: " + police.getPhoneNo());
//                                policeID = police.getId();
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Police not found1",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "Police not found",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        queue.add(jsArray);
//    }

    public void UpdatePolice() {
        final String url = "https://driverchecker.000webhostapp.com/update_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                userNameDriver.setText("");
                NameDriver.setText("");
                passwordDriver.setText("");
                phoneNoDriver.setText("");
                JobDriver.setText("");
                genderDriver.setText("");
                addressDriver.setText("");
                Toast.makeText(getContext(), "Update Police done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams() {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", userNamePolice.getText().toString());
                map.put("name", NamePolice.getText().toString() + " " + NameDriver.getText().toString());
                map.put("password", passwordPolice.getText().toString());
                map.put("phone", phoneNoPolice.getText().toString());
                map.put("dgree", dgreePolice.getText().toString());
                map.put("jobID", jobIdPolice.getText().toString());
                map.put("address", addressPolice.getText().toString());


                return map;
            }


        };

        queue.add(request);


    }


    public void DeletePolice(String police_id) {
        String url = "https://driverchecker.000webhostapp.com/delete_polve.php?police_id=" + police_id +"";
        Log.d("url", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("url",response.toString());
                        userNamePolice.setText("");
                        NamePolice.setText("");
                        passwordPolice.setText("");
                        phoneNoPolice.setText("");
                        jobIdPolice.setText("");
                        dgreePolice.setText("");
                        addressPolice.setText("");
                        Toast.makeText(getContext(), "Delete Police done", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });

        queue.add(jsonObjectRequest);


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

