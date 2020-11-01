package com.example.driver.ui.manager.Manager.add_Police;

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
import android.text.TextWatcher;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.Volley;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDB;
import com.example.driver.DataBaseRoom.Tables.Manager.ManagerDao;
import com.example.driver.LoadingDialog;
import com.example.driver.LoginActivity;
import com.example.driver.NukeSSLCerts;
import com.example.driver.R;
import com.example.driver.VolleyMultipartRequest;
import com.example.driver.VolleySingleton;
import com.example.driver.ui.UploadImageApacheHttp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static final String TAG = "Upload Image";
    LoadingDialog loadingDialog;
    public static final String UPLOAD_URL = "https://driverchecker.000webhostapp.com/insert_police.php";

    public static final String UPLOAD_KEY = "upload_image";

    private int PICK_IMAGE_REQUEST = 100;
    private ImageView imgView;
    private Bitmap bitmap;
    private Uri filePath;
    private Object selectedFilePath;
    byte[] byteArray = null;
    private String mediaPath = "";


    private HomeViewModel homeViewModel;
    TextInputLayout userName, password, rePassword, firstName, lastName, phoneNo, jobID, dgree, address;
    TextInputEditText txt_userName, txt_password, txt_rePassword, txt_firstName, txt_lastName, txt_phoneNo, txt_jobID, txt_dgree, txt_address;
    Button btn_signup;
    ImageView imageView;
    JSONObject jsonObject;
    RequestQueue queue;
    ManagerDao managerDao;
    ManagerDB manager;
    Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        IdentifyMethod(root);
        loadingDialog = new LoadingDialog(getActivity());
        txt_userName.addTextChangedListener(new TextWatcher() {
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

        txt_password.addTextChangedListener(new TextWatcher() {
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

        txt_rePassword.addTextChangedListener(new TextWatcher() {
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

        txt_firstName.addTextChangedListener(new TextWatcher() {
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

        txt_lastName.addTextChangedListener(new TextWatcher() {
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

        txt_phoneNo.addTextChangedListener(new TextWatcher() {
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

        txt_jobID.addTextChangedListener(new TextWatcher() {
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

        txt_dgree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateDgree(s.toString());

            }


        });

        txt_address.addTextChangedListener(new TextWatcher() {
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

        //addPolice();
        NukeSSLCerts.nuke();
        queue = Volley.newRequestQueue(getContext());


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validateName(txt_userName.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid name", Toast.LENGTH_SHORT).show();
                } else if (!validatePassword(txt_password.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid password", Toast.LENGTH_SHORT).show();
                } else if (!validateRePassword(txt_rePassword.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid password", Toast.LENGTH_SHORT).show();
                } else if (!validateFirstName(txt_firstName.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid name", Toast.LENGTH_SHORT).show();
                } else if (!validateLastName(txt_lastName.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid name", Toast.LENGTH_SHORT).show();
                } else if (!validatePhone(txt_phoneNo.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid name", Toast.LENGTH_SHORT).show();
                } else if (!validateDgree(txt_dgree.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid phone", Toast.LENGTH_SHORT).show();
                } else if (!validateJob(txt_jobID.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid car number", Toast.LENGTH_SHORT).show();
                } else if (!validateAddress(txt_address.getText().toString())) {
                    Toast.makeText(getContext(), "Pleas enter a valid car type", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.startLoadingDialog();
                    insertPoliceWithImage();

                }


            }
        });

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;

    }

//    final LoadingDialog loadingDialog = new LoadingDialog(HomeFragment.super.getActivity());


    public void IdentifyMethod(View root) {
        userName = root.findViewById(R.id.txt_layout_username_p);
        txt_userName = root.findViewById(R.id.txt_userName_police);
        password = root.findViewById(R.id.txt_layout_password_p);
        txt_password = root.findViewById(R.id.txt_password_police);
        rePassword = root.findViewById(R.id.txt_layout_repassword_p);
        txt_rePassword = root.findViewById(R.id.rePassword_police);
        firstName = root.findViewById(R.id.txt_layout_fname_p);
        txt_firstName = root.findViewById(R.id.txt_fName_police);
        lastName = root.findViewById(R.id.txt_layout_lname_p);
        txt_lastName = root.findViewById(R.id.txt_lName_police);
        phoneNo = root.findViewById(R.id.txt_layout_phoneNo_p);
        txt_phoneNo = root.findViewById(R.id.txt_phoneNo_police);
        address = root.findViewById(R.id.txt_layout_address_p);
        txt_address = root.findViewById(R.id.txt_address_police);
        jobID = root.findViewById(R.id.txt_layout_job_p);
        txt_jobID = root.findViewById(R.id.txt_job_id);
        dgree = root.findViewById(R.id.txt_layout_dgree_p);
        txt_dgree = root.findViewById(R.id.txt_dgree_police);
        imageView = root.findViewById(R.id.img_add_user_police);
        btn_signup = root.findViewById(R.id.btn_SignUp_police);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });


    }


    private boolean validateName(String name) {

        if (name.trim().isEmpty()) {
            userName.setError("Name can't be empty");
            return false;
        } else if (!name.trim().matches("[a-zA-Z]+")) {
            userName.setError("Name can only contain letters");
            return false;
        } else {
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(String pass) {


        if (pass.trim().isEmpty()) {
            password.setError("Password can't be empty");
            password.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;

        }
        return true;

    }

    private boolean validateRePassword(String repass) {


        if (repass.trim().isEmpty()) {
            rePassword.setError("Password can't be empty");
            rePassword.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return false;

        } else if (repass.equals(txt_password == txt_rePassword)) {
            rePassword.setError("Error");
            return false;
        }


        return true;
    }

    private boolean validateFirstName(String fname) {

        if (fname.trim().isEmpty()) {
            firstName.setError("Name can't be empty");
            return false;
        } else if (!fname.trim().matches("[a-zA-Z]+")) {
            firstName.setError("Name can only contain letters");
            return false;
        } else {
            firstName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLastName(String lname) {

        if (lname.trim().isEmpty()) {
            lastName.setError("Name can't be empty");
            return false;
        } else if (!lname.trim().matches("[a-zA-Z]+")) {
            lastName.setError("Name can only contain letters");
            return false;
        } else {
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(String phone) {

        if (phone.trim().isEmpty()) {
            phoneNo.setError("Phone Number can't be empty");
            phoneNo.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return false;

        }

        if (phone.length() < 9) {
            phoneNo.setError("Phone Number can't be more than 9 numbers");
            phoneNo.setErrorTextColor(ColorStateList.valueOf(Color.BLACK));
            return false;
        } else {
            phoneNo.setEnabled(false);
        }

        return true;
    }

    private boolean validateJob(String job) {

        if (job.trim().isEmpty()) {
            jobID.setError("Job can't be empty");
            return false;
        } else if (!job.trim().matches("[a-zA-Z]+")) {
            jobID.setError("Job can only contain letters");
            return false;
        } else {
            jobID.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDgree(String type) {

        if (type.trim().isEmpty()) {
            dgree.setError("Name can't be empty");
            return false;
        } else if (!type.trim().matches("[a-zA-Z]+")) {
            dgree.setError("Name can only contain letters");
            return false;
        } else {
            dgree.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress(String home) {

        if (home.trim().isEmpty()) {
            address.setError("Name can't be empty");
            return false;
        } else if (!home.trim().matches("[a-zA-Z]+") && !home.trim().matches("[0-10]+")) {
            address.setError("Address can only contain letters and Numbers");
            return false;
        } else {
            address.setErrorEnabled(false);
            return true;
        }
    }


    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);


    }


//    public void setPoliceData() {
//        final String url = "https://driverchecker.000webhostapp.com/insert_police.php";
//
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                userName.setText("");
//                firstName.setText("");
//                lastName.setText("");
//                password.setText("");
//                rePassword.setText("");
//                phoneNo.setText("");
//                jobID.setText("");
//                dgree.setText("");
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
//            protected Map<String, String> getParams()  {
//
//
//                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("user_name", userName.getText().toString());
//                map.put("name", firstName.getText().toString() + " " + lastName.getText().toString());
//                map.put("password", password.getText().toString());
//                map.put("phone", phoneNo.getText().toString());
//                map.put("dgree", dgree.getText().toString());
//                map.put("jobID", jobID.getText().toString());
//                map.put("address", address.getText().toString());
//
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


    void insertPoliceWithImage() {
        final String url = "https://driverchecker.000webhostapp.com/insert_police.php";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.d("response1", resultResponse);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    JSONObject result2 = result.getJSONObject("result");


                    String message = result2.getString("err_msg");
                    int errNo = result2.getInt("err_no");


                    if (errNo > 0) {

                        loadingDialog.dismissDialog();
                        Toast.makeText(getContext(), "error message: " + message, Toast.LENGTH_SHORT).show();

                    }


                    // tell everybody you have succed upload image and post strings
                    Log.d("response1", message);


                    Log.i("Unexpected", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingDialog.dismissDialog();
                }

                //Log.d("response1",response.allHeaders.get(0).getValue());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                // Log.d("response",error.getMessage()+"--- " +networkResponse.statusCode);
                error.printStackTrace();
                loadingDialog.dismissDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();

                map.put("user_name", txt_userName.getText().toString());
                map.put("name", txt_firstName.getText().toString() + " " + txt_lastName.getText().toString());
                map.put("password", txt_password.getText().toString());
                map.put("phone", txt_phoneNo.getText().toString());
                map.put("dgree", txt_dgree.getText().toString());
                map.put("jobID", txt_jobID.getText().toString());
                map.put("address", txt_address.getText().toString());

                return map;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

                if (byteArray == null) {

                    params.put("file", new DataPart("", new byte[0]));

                } else {

                    params.put("file", new DataPart(txt_userName.getText().toString() + ".png", byteArray, "image/*"));

                }


                //DataPart second parameter is byte[]
                return params;
            }
        };


        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
        //  requestMulitiPart.ge
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

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

                imageView.setImageDrawable(getContext().getDrawable(R.drawable.policeman));


            } else {

                imageView.setImageURI(uri);

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
        }
//            Uri uri = data.getData();
//            imageView.setImageURI(uri);
//           }


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