package com.example.driver.ui.manager.Manager.add_Police;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.driver.NukeSSLCerts;
import com.example.driver.R;
import com.example.driver.ui.UploadImageApacheHttp;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static final String TAG = "Upload Image";

    public static final String UPLOAD_URL = "https://driverchecker.000webhostapp.com/insert_police.php";

    public static final String UPLOAD_KEY = "upload_image";

    private int PICK_IMAGE_REQUEST = 100;
    private ImageView imgView;
    private Bitmap bitmap;
    private Uri filePath;
    private Object selectedFilePath;


    private HomeViewModel homeViewModel;
    EditText userName, password, rePassword, firstName, lastName, phoneNo, jobID, dgree, address;
    Button btn_signup;
    JSONObject jsonObject;
    RequestQueue queue;
    ManagerDao managerDao;
    ManagerDB manager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        IdentifyMethod();
        //addPolice();
        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(getContext());

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPoliceData();
            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;

    }

    public void IdentifyMethod()
    {
        userName = userName.findViewById(R.id.txt_userName_police);
        password = password.findViewById(R.id.txt_password_police);
        rePassword = rePassword.findViewById(R.id.rePassword_police);
        firstName = firstName.findViewById(R.id.txt_fName_police);
        lastName = lastName.findViewById(R.id.txt_lName__police);
        phoneNo = phoneNo.findViewById(R.id.txt_phoneNo_police);
        address = address.findViewById(R.id.txt_address_police);
        jobID = jobID.findViewById(R.id.txt_job_id);
        dgree = dgree.findViewById(R.id.txt_dgree_police);
        btn_signup = btn_signup.findViewById(R.id.btn_SignUp_police);
    }


    public void setPoliceData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                userName.setText("");
                firstName.setText("");
                lastName.setText("");
                password.setText("");
                rePassword.setText("");
                phoneNo.setText("");
                jobID.setText("");
                dgree.setText("");
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
                map.put("dgree", dgree.getText().toString());
                map.put("jobID", jobID.getText().toString());
                map.put("address", address.getText().toString());


                return map;
            }


        };

        queue.add(request);


    }
    Handler handler = handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "Handler " + msg.what);
            if (msg.what == 1) {
                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void showFileChooser () {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            selectedFilePath = getPath(filePath);
            Log.i(TAG, " File path : " + selectedFilePath);
            try {
                bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) getPath(filePath), filePath);
                imgView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getPath (Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void uploadImage () {

        UploadImageApacheHttp uploadTask = new UploadImageApacheHttp();
        uploadTask.doFileUpload(UPLOAD_URL, String.valueOf(bitmap), handler);

    }

    public void onClick (View v){
        if (v == btn_signup)
            showFileChooser();
        else {
            Toast.makeText(getContext(), "Start Uploading", Toast.LENGTH_SHORT).show();
            uploadImage();
        }
    }

//    public void setter() {
//        manager.getUserName(userName.getText().toString().trim());
//        manager.setPassword(Integer.parseInt(password.getText().toString().trim()));
//        manager.setRePassword(Integer.parseInt(rePassword.getText().toString().trim()));
//        manager.setPoliceFirstName(firstName.getText().toString().trim());
//        manager.setPoliceLastName(lastName.getText().toString().trim());
//        manager.setPhone(Integer.parseInt(phoneNo.getText().toString().trim()));
//        manager.setAdress(address.getText().toString().trim());
//        manager.setUserID(Integer.parseInt(jobID.getText().toString().trim()));
//        manager.setDgree(dgree.getText().toString().trim());
//    }
//
//
//    private void addPolice() {
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

}