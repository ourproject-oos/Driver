package com.example.driver.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class DashboardFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private DashboardViewModel dashboardViewModel;
    EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob, carNumber, carType, address;
    Button btn_signup;
    RoundedImageView roundedImageView;
    JSONObject jsonObject;
    RequestQueue queue;
    Bitmap FixBitmap;
    URL url;
    HttpURLConnection httpURLConnection;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    int RC;
    StringBuilder stringBuilder;
    boolean check = true;
    private RadioGroup radioGroup;
    private String gender = "male";
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

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
        roundedImageView = root.findViewById(R.id.img_add_user);
        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(root.getContext());
        radioGroup = root.findViewById(R.id.rg_gender);
        radioGroup.setOnCheckedChangeListener(this);

        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


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
                //         textView.setTe
                //         xt(s);


            }
        });
        return root;


    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            roundedImageView.setImageURI(imageUri);
        }
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
