package com.example.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassword;
    String userName, password, fetchUserName, fetchPassword, major;
    List<Police> policeList;
    Police police;
    List<Driver> driverList;
    Driver driver;
    RequestQueue queue;
    Spinner spinner;
    List<Manager> managerList;
    Manager manager;
    ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        edUserName = findViewById(R.id.userName_ed_text_login);
        edPassword = findViewById(R.id.password_ed_text_login);
        spinner = findViewById(R.id.Spinner_major);
        spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        final String[] majorList = {"MANAGER", "POLICE", "DRIVER"};
        spinnerAdapter.addAll(majorList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                major = adapterView.getSelectedItem().toString();
                Toast.makeText(LoginActivity.this, major, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(this);
        Toast.makeText(this, "" + queue.getSequenceNumber(), Toast.LENGTH_SHORT).show();
        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edUserName.getText().toString().indexOf(" ") > 0) {
                    edUserName.setError("write username with out space");
                } else if (edUserName.getText().toString().indexOf(" ") == 0) {
                    edUserName.setError("can not start with space");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

    public void regOnClick(View view) {
        loadingDialog.startLoadingDialog();
        getUserData();

    }

    public void getUserData() {
        userName = edUserName.getText().toString();
        password = edPassword.getText().toString();

        JsonArrayRequest jsArray = new JsonArrayRequest("https://driverchecker.000webhostapp.com/" +
                "fetch_user.php?user_name=" + userName + "&password=" + password + "&major=" + major + "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //JSONArray departmentArrayJson = response.getJSONArray("department");
                        try {
                            if (major.equals("POLICE")) {
                                policeList = new ArrayList<>();

                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject js = response.getJSONObject(i);
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
                                if (policeList.size() == 0) {
                                    Toast.makeText(LoginActivity.this, "username or Password or Major is error",
                                            Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismissDialog();

                                } else {

                                    SharedPreferences sharedPreferencesUser =
                                            LoginActivity.this.getSharedPreferences("user_sp", MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                                    editor.putInt("ID", police.getId());
                                    editor.putString("userName", police.getUserName());
                                    editor.putString("fullName", police.getName());
                                    editor.putString("phone", police.getPhoneNo());
                                    editor.putString("dgree", police.getDgree());
                                    editor.putString("job_id", police.getJob_id());
                                    editor.putString("address", police.getAddress());
                                    editor.putString("lat", police.getLat());
                                    editor.putString("lang", police.getLang());
                                    editor.putString("major", major);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainAllActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                            } else if (major.equals("driver")) {


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
                                    Toast.makeText(LoginActivity.this, "username or Password or Major is error",
                                            Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismissDialog();
                                } else {
                                    SharedPreferences sharedPreferencesUser =
                                            LoginActivity.this.getSharedPreferences("user_sp", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                                    editor.putInt("ID", driver.getId());
                                    editor.putString("userName", driver.getUserName());
                                    editor.putString("fullName", driver.getName());
                                    editor.putString("phone", driver.getPhoneNo());
                                    editor.putString("address", driver.getAddress());
                                    editor.putString("major", major);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainAllActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();


                                }
                            } else {
                                managerList = new ArrayList<>();

                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject js = response.getJSONObject(i);
                                    manager = new Manager();
                                    manager.setId(js.getInt("ID"));
                                    manager.setUser(js.getString("USER_NAME"));
                                    manager.setName(js.getString("NAME"));
                                    manager.setPassword(js.getString("PASSWORD"));
                                    manager.setPhone(js.getString("PHONE"));

                                    managerList.add(manager);
                                }
                                if (managerList.size() == 0) {
                                    Toast.makeText(LoginActivity.this, "username or Password or Major is error",
                                            Toast.LENGTH_SHORT).show();
                                    loadingDialog.dismissDialog();
                                } else {
                                    SharedPreferences sharedPreferencesUser =
                                            LoginActivity.this.getSharedPreferences("user_sp", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferencesUser.edit();
                                    editor.putInt("ID", manager.getId());
                                    editor.putString("userName", manager.getUser());
                                    editor.putString("fullName", manager.getName());
                                    editor.putString("phone", manager.getPhone());
                                    editor.putString("major", major);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainAllActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "username or Password or Major is error",
                                    Toast.LENGTH_SHORT).show();
                            loadingDialog.dismissDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "no internet connection",
                        Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }


        });

        queue.add(jsArray);
    }
}
