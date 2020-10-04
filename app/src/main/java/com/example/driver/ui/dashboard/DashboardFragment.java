package com.example.driver.ui.dashboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.NukeSSLCerts;
import com.example.driver.R;

import org.json.JSONObject;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        userName = root.findViewById(R.id.txt_userName_driver);
        password = root.findViewById(R.id.txt_password);
        rePassword = root.findViewById(R.id.rePassword);
        firstName = root.findViewById(R.id.txt_fName_diver);
        lastName = root.findViewById(R.id.txt_lName_driver);
        phoneNo = root.findViewById(R.id.txt_phoneNo);
        address = root.findViewById(R.id.txt_address);
        carNumber = root.findViewById(R.id.txt_car_number);
        carType = root.findViewById(R.id.txt_car_type);
        userJob = root.findViewById(R.id.text_job);
        btn_signup = root.findViewById(R.id.btn_SignUp);
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
}
