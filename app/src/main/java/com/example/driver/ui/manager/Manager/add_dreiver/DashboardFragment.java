package com.example.driver.ui.manager.Manager.add_dreiver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.Notifications.Data;
import com.example.driver.Notifications.MyResponse;
import com.example.driver.Notifications.NotificationSender;
import com.example.driver.Notifications.Token;
import com.example.driver.NukeSSLCerts;
import com.example.driver.Police;
import com.example.driver.R;
import com.example.driver.ui.UploadImageApacheHttp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

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
    private Bitmap bitmap;
    private Uri filePath;
    private Object selectedFilePath;
    private APIService apiService;


    private DashboardViewModel dashboardViewModel;
    EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob, carNumber,cardDate, licence, carType, address;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        IdentifyMethod(root);
        // addDriver();
        NukeSSLCerts.nuke();
//        UpdateToken();
        queue = Volley.newRequestQueue(root.getContext());

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


        btn_SignUp.setOnClickListener(new View.OnClickListener() {
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

    public void IdentifyMethod(View root) {
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
        cardDate = root.findViewById(R.id.txt_card_date);
        licence = root.findViewById(R.id.txt_licence);
        imageView = root.findViewById(R.id.img_add_user);
        btn_SignUp = root.findViewById(R.id.btn_SignUp);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        radioGroup = root.findViewById(R.id.rg_gender);

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
                cardDate.setText("");
                licence.setText("");
                address.setText("");
                imageView.setImageBitmap(bitmap);
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
                map.put("card_date", cardDate.getText().toString());
                map.put("licence", licence.getText().toString());
                map.put("address", address.getText().toString());
                map.put("image", String.valueOf(imageView));

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
                    Toast.makeText(getActivity(), "Upload Field", Toast.LENGTH_SHORT).show();
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

        public Object getPath ( Uri uri){
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
