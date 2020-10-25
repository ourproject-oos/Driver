package com.example.driver.ui.manager.Manager.add_dreiver;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import retrofit2.Call;
import retrofit2.Callback;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.Volley;
import com.example.driver.AppHelper;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDB;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDao;
import com.example.driver.Driver;
import com.example.driver.Manager;
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.Notifications.Data;
import com.example.driver.Notifications.MyResponse;
import com.example.driver.Notifications.NotificationSender;
import com.example.driver.Notifications.Token;
import com.example.driver.NukeSSLCerts;
import com.example.driver.Police;
import com.example.driver.R;
import com.example.driver.VolleyMultipartRequest;
import com.example.driver.VolleySingleton;
import com.example.driver.ui.UploadImageApacheHttp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    public static final String TAG = "Upload Image";
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

    // private static final int PICK_IMAGE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewjmById(R.id.text_dashboard);

        IdentifyMethod(root);
        // addDriver();
        edt_userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateName(s.toString());


            }


        });

        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validatePassword(s.toString());

            }


        });

        edt_rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateRePassword(s.toString());


            }


        });

        edt_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFirstName(s.toString());


            }


        });

        edt_lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validateLastName(s.toString());

            }


        });

        edt_phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validatePhone(s.toString());

            }


        });

        edt_carNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validateCarNumber(s.toString());

            }


        });

        edt_carType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateCarType(s.toString());

            }


        });

        edt_cardDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateCardDate(s.toString());

            }


        });

        edt_licence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLicence(s.toString());

            }


        });

        edt_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validateAddress(s.toString());


            }


        });

        edt_userJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                validateJob(s.toString());


            }


        });


        NukeSSLCerts.nuke();
//        UpdateToken();
        queue = Volley.newRequestQueue(root.getContext());

        radioGroup.setOnCheckedChangeListener(this);


        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setDriverData();
//                Toast.makeText(getContext(), "777777", Toast.LENGTH_SHORT).show();
                insertDriverWithImage();


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

    public void IdentifyMethod(View root) {

        userName=root.findViewById(R.id.txt_layout_username_d);
        edt_userName = root.findViewById(R.id.txt_userName_driver);
        password=root.findViewById(R.id.txt_layout_password_d);
        edt_password = root.findViewById(R.id.txt_password);
        rePassword =root.findViewById(R.id.txt_layout_repassword_d);
        edt_rePassword = root.findViewById(R.id.rePassword);
        firstName=root.findViewById(R.id.txt_layout_fname_d);
        edt_firstName = root.findViewById(R.id.txt_fName_diver);
        lastName=root.findViewById(R.id.txt_layout_lname_d);
        edt_lastName = root.findViewById(R.id.txt_lName_driver);
        phoneNo=root.findViewById(R.id.txt_layout_phoneNo_d);
        edt_phoneNo = root.findViewById(R.id.txt_phoneNo);
        address=root.findViewById(R.id.txt_layout_address_d);
        edt_address = root.findViewById(R.id.txt_address);
        carNumber=root.findViewById(R.id.txt_layout_car_number_d);
        edt_carNumber = root.findViewById(R.id.txt_car_number);
        carType=root.findViewById(R.id.txt_layout_car_type_d);
        edt_carType = root.findViewById(R.id.txt_car_type);
        userJob=root.findViewById(R.id.txt_layout_job_d);
        edt_userJob = root.findViewById(R.id.text_job);
        cardDate=root.findViewById(R.id.txt_layout_card_date_d);
        edt_cardDate = root.findViewById(R.id.txt_card_date);
        licence=root.findViewById(R.id.txt_layout_licence_d);
        edt_licence = root.findViewById(R.id.txt_licence);
        imageView = root.findViewById(R.id.img_add_user);
        btn_SignUp = root.findViewById(R.id.btn_SignUp);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        radioGroup = root.findViewById(R.id.rg_gender);
//        userName.setText("");
//        password.setText("");
//        rePassword.setText("");
//        firstName.setText("");
//        lastName.setText("");
//        phoneNo.setText("");
//        address.setText("");
//        carNumber.setText("");
//        carType.setText("");
//        userJob.setText("");
//        licence.setText("");
//        cardDate.setText("");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }

    private boolean validateName(String name) {

        if (name.trim().isEmpty())
        {
            userName.setError("Name can't be empty");
            return false;
        }
        else if (!name.trim().matches("[a-zA-Z]+"))
        {
            userName.setError("Name can only contain letters");
            return false;
        }
        else
        {
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(String pass) {


        if (pass.trim().isEmpty())
        {
            password.setError("Password can't be empty");
            password.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;

        }
        else if (pass.length()<4)
        {
            password.setError("Weak");
            return false;
        }

        else if (pass.length()<7)
        {
            password.setError("Medium");
            return false;

        }

        else if (pass.length()<10)
        {
            password.setError("Strong");
            password.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return true;
        }

        else if (pass.length()<20)
        {
            password.setError("Very Strong");
            password.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return true;
        }

        return true;
    }

    private boolean validateRePassword(String repass) {


        if (repass.trim().isEmpty())
        {
            rePassword.setError("Password can't be empty");
            rePassword.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;

        }
        else if (repass.length()<4)
        {
            rePassword.setError("Weak");
            return false;
        }

        else if (repass.length()<7)
        {
            rePassword.setError("Medium");
            return false;

        }

        else if (repass.length()<10)
        {
            rePassword.setError("Strong");
            rePassword.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return true;
        }

        else if (repass.length()<20)
        {
            rePassword.setError("Very Strong");
            rePassword.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return true;
        }

        return true;
    }

    private boolean validateFirstName(String fname) {

        if (fname.trim().isEmpty())
        {
            firstName.setError("Name can't be empty");
            return false;
        }
        else if (!fname.trim().matches("[a-zA-Z]+"))
        {
            firstName.setError("Name can only contain letters");
            return false;
        }
        else
        {
            firstName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName(String lname) {

        if (lname.trim().isEmpty())
        {
            lastName.setError("Name can't be empty");
            return false;
        }
        else if (!lname.trim().matches("[a-zA-Z]+"))
        {
            lastName.setError("Name can only contain letters");
            return false;
        }
        else
        {
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(String phone) {

        if (phone.trim().isEmpty())
        {
            phoneNo.setError("Phone Number can't be empty");
            phoneNo.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return false;

        }

          if (phone.length() < 9) {
             phoneNo.setError("Phone Number can't be more than 9 numbers");
             phoneNo.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return false;
        }
          else
          {
              phoneNo.setEnabled(false);
          }

        return true;
    }

    private boolean validateCarNumber(String carnum) {

        if (carnum.trim().isEmpty())
        {
            carNumber.setError("Car Number can't be empty");
            return false;
        }
        else if (!carnum.trim().matches("[a-zA-Z]+") && !carnum.trim().matches("[0-10]+"))
        {
            carNumber.setError("Name can only contain letters");
            return false;
        }
        else
        {
            carNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCarType(String type) {

        if (type.trim().isEmpty())
        {
            carType.setError("Car Type can't be empty");
            return false;
        }
        else if (!type.trim().matches("[a-zA-Z]+") && !type.trim().matches("[0-10]+"))
        {
            carType.setError("Car Type can only contain letters");
            return false;
        }
        else
        {
            carType.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCardDate(String card) {

        if (card.trim().isEmpty())
        {
            cardDate.setError("Name can't be empty");
            return false;
        }
        else if (!card.trim().matches("[Date]+"))
        {
            cardDate.setError("Card Date can only contain Date ");
            return false;
        }
        else
        {
            cardDate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLicence(String lec) {

        if (lec.trim().isEmpty())
        {
            licence.setError("Name can't be empty");
            return false;
        }
        else if (!lec.trim().matches("[a-zA-Z]+") && !lec.trim().matches("[0-10]+"))
        {
            licence.setError("Name can only contain letters and Numbers");
            return false;
        }
        else
        {
            licence.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress(String addre) {

        if (addre.trim().isEmpty())
        {
            address.setError("Address can't be empty");
            return false;
        }
        else if (!addre.trim().matches("[a-zA-Z]+") && !addre.trim().matches("[0-10]+"))
        {
            address.setError("Address can only contain letters and Numbers");
            return false;
        }
        else
        {
            address.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateJob(String job) {

        if (job.trim().isEmpty())
        {
            userJob.setError("Job can't be empty");
            return false;
        }
        else if (!job.trim().matches("[a-zA-Z]+"))
        {
            userJob.setError("Job can only contain letters");
            return false;
        }
        else
        {
            userJob.setErrorEnabled(false);
            return true;
        }
    }





    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);


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


    void insertDriverWithImage()
    {
        final String url = "https://driverchecker.000webhostapp.com/insert_driver.php";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.d("response1", resultResponse);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    JSONObject result2=result.getJSONObject("result");


                    String message = result2.getString("err_msg");


                        // tell everybody you have succed upload image and post strings
                        Log.d("response1", message);


                        Log.i("Unexpected", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Log.d("response1",response.allHeaders.get(0).getValue());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                Log.d("response",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("user_name", edt_userName.getText().toString());
                map.put("name", edt_firstName.getText().toString() + " " + edt_lastName.getText().toString());
                map.put("password", edt_password.getText().toString());
                map.put("phone", edt_phoneNo.getText().toString());
                map.put("job", edt_userJob.getText().toString());
                map.put("gender", gender);
                map.put("car_type", edt_carType.getText().toString());
                map.put("car_no", edt_carNumber.getText().toString());
                map.put("card_date", edt_cardDate.getText().toString());
                map.put("licence", edt_licence.getText().toString());
                map.put("address", edt_address.getText().toString());
                return map;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

if(byteArray==null){

    params.put("file", new DataPart("", new byte[0]));

}else{

    params.put("file", new DataPart(edt_userName.getText().toString()+".png", byteArray, "image/*"));

}



                //DataPart second parameter is byte[]
                return params;
            }
        };


        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
        //  requestMulitiPart.ge
    }

//    public void setDriverData() {
//        final String url = "https://driverchecker.000webhostapp.com/insert_driver.php";
//
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                userName.setText("");K
//                firstName.setText("");
//                lastName.setText("");
//                password.setText("");
//                rePassword.setText("");
//                phoneNo.setText("");
//                userJob.setText("");
//                carType.setText("");
//                carNumber.setText("");
//                cardDate.setText("");
//                licence.setText("");
//                address.setText("");
//                Toast.makeText(getContext(), "insert done", Toast.LENGTH_SHORT).show();
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//
//        }) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("user_name", userName.getText().toString());
//                map.put("name", firstName.getText().toString() + " " + lastName.getText().toString());
//                map.put("password", password.getText().toString());
//                map.put("phone", phoneNo.getText().toString());
//                map.put("job", userJob.getText().toString());
//                map.put("gender", gender);
//                map.put("car_type", carType.getText().toString());
//                map.put("car_no", carNumber.getText().toString());
//                map.put("card_date", cardDate.getText().toString());
//                map.put("licence", licence.getText().toString());
//                map.put("address", address.getText().toString());
//                return map;
//            }
//
//
//        };
//
//        queue.add(request);
//
//
//    }


    Handler handler = handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "Handler " + msg.what);
            if (msg.what == 1) {
                Toast.makeText(getActivity(), "Upload Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Upload Field", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 222) {
            imageUri = data.getData();

            //  imageView.setImageBitmap(bitmap1);
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {

                //  bitmap1 = MediaStore.Images.Media.getBitmap((ContentResolver) getPath(imageUri), imageUri);

                //

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn,
                        null, null, null);
                assert cursor != null;

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                Log.d("req", mediaPath);

            } catch (Exception e) {

                e.printStackTrace();

            }
//            filePath = data.getData();
//            selectedFilePath = getPath(filePath);
//            Log.i(TAG, " File path : " + selectedFilePath);
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) getPath(filePath), filePath);
//                imgView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            Uri uri = data.getData();
            if (!uri.isAbsolute()) {

                imageView.setImageDrawable(getContext().getDrawable(R.drawable.image_driver));


            } else {

                imageView.setImageURI(uri);

            }


        }


    }

    public Object getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void uploadImage() {
        UploadImageApacheHttp uploadTask = new UploadImageApacheHttp();
        uploadTask.doFileUpload(UPLOAD_URL, String.valueOf(bitmap), handler);
    }

    public void onClick(View v) {
        if (v == btn_SignUp)
            showFileChooser();
        else {
            Toast.makeText(getContext(), "Start Uploading", Toast.LENGTH_SHORT).show();
            uploadImage();
        }
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
