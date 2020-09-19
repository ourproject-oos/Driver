package com.example.driver.ui.add_dreiver;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDB;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDao;
import com.example.driver.Driver;
import com.example.driver.Manager;
import com.example.driver.NukeSSLCerts;
import com.example.driver.Police;
import com.example.driver.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private DashboardViewModel dashboardViewModel;
    EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob, carNumber, carType, address;
    Button btn_signup;
    JSONObject jsonObject;
    RequestQueue queue;
    private RadioGroup radioGroup;
    private String gender = "male";
    ArrayList<Manager> list;
    ManagerDB manager;
    ManagerDao managerDao;
    Driver driver;
    Police police;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        IdentifyMethod();
        addDriver();
        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(root.getContext());

        radioGroup = root.findViewById(R.id.rg_gender);
        radioGroup.setOnCheckedChangeListener(this);

        rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!rePassword.getText().toString().equals(password.getText().toString()))
                    rePassword.setError("password not match");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!rePassword.getText().toString().equals(password.getText().toString()))
                    rePassword.setError("password not match");

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!rePassword.getText().toString().equals(password.getText().toString()))
                    rePassword.setError("password not match");
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDriverData();

            }
        });
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //         textView.setText(s);


            }
        });
        return root;
    }

    public void IdentifyMethod() {
        userName = userName.findViewById(R.id.txt_userNameSignup);
        password = password.findViewById(R.id.txt_password);
        rePassword = rePassword.findViewById(R.id.rePassword);
        firstName = firstName.findViewById(R.id.txt_fName);
        lastName = lastName.findViewById(R.id.txt_lName);
        phoneNo = phoneNo.findViewById(R.id.txt_phoneNo);
        address = address.findViewById(R.id.txt_address);
        carNumber = carNumber.findViewById(R.id.txt_car_number);
        carType = carType.findViewById(R.id.txt_car_type);
        userJob = userJob.findViewById(R.id.text_job);
        btn_signup = btn_signup.findViewById(R.id.btn_SignUp);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.rb_male:
                gender = "male";

                break;
            case R.id.rb_female:
                gender = "female";
                break;
        }
        Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
    }

    public void setDriverData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_driver.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                userName.setText("");
                firstName.setText("");
                lastName.setText("");
                password.setText("");
                rePassword.setText("");
                phoneNo.setText("");
                userJob.setText("");
                carType.setText("");
                carNumber.setText("");
                address.setText("");
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
                map.put("user_name", userName.getText().toString());
                map.put("name", firstName.getText().toString() + " " + lastName.getText().toString());
                map.put("password", password.getText().toString());
                map.put("phone", phoneNo.getText().toString());
                map.put("job", userJob.getText().toString());
                map.put("gender", gender);
                map.put("car_type", carType.getText().toString());
                map.put("car_no", carNumber.getText().toString());
                map.put("address", address.getText().toString());
                return map;
            }


        };

        queue.add(request);


    }


    public void setter() {
        manager.getUserName(userName.getText().toString().trim());
        manager.setPassword(Integer.parseInt(password.getText().toString().trim()));
        manager.setRePassword(Integer.parseInt(rePassword.getText().toString().trim()));
        manager.setDriverFirstName(firstName.getText().toString().trim());
        manager.setDriverLastName(lastName.getText().toString().trim());
        manager.setPhone(Integer.parseInt(phoneNo.getText().toString().trim()));
        manager.setAdress(address.getText().toString().trim());
        manager.setCarNumber(Integer.parseInt(carNumber.getText().toString().trim()));
        manager.setCarType(carType.getText().toString().trim());
        manager.setUserJob(userJob.getText().toString().trim());
    }


    private void addDriver() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setter();
                managerDao.insertManager(manager);

                Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
            }
        });

    }
}