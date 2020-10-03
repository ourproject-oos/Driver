package com.example.driver.ui.manager.Manager.update_delete_user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver.Notifications.APIService;
import com.example.driver.Notifications.Client;
import com.example.driver.R;
import com.example.driver.VioAdapter;
import com.example.driver.VioClass;
import com.example.driver.ui.driver.SlideshowViewModel;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.driver.ui.manager.Manager.add_dreiver.DashboardFragment.TAG;

public class update_delete extends Fragment {

    String Message;
    private UpdateDeleteViewModle update_deleteViewModel;
    private File f;
    private SharedPreferences sharedPreferences;
    int driverID;
    int policeID;
    VioAdapter vioAdapter;
    TextView Name , phone, job,card_date, licence,address, dgree;
    List<VioClass> vioClassList = new ArrayList<>();
    VioClass vioClass;
    private RequestQueue queue;
    RecyclerView recyclerView;
    APIService apiService;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        update_deleteViewModel =
                ViewModelProviders.of(this).get(UpdateDeleteViewModle.class);
        View root = inflater.inflate(R.layout.update_delete_user, container, false);
        queue = Volley.newRequestQueue(getContext());
        recyclerView = root.findViewById(R.id.update_driver_police_re);

        Identify(root);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        f = new File("/data/data/" + getContext().getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            driverID = sharedPreferences.getInt("ID", 0);
            Toast.makeText(getContext(), String.valueOf(driverID), Toast.LENGTH_SHORT).show();
        }


        update_deleteViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        setDriverData();
        return root;



    }





    public void Identify (View root)    {

        Name = root.findViewById(R.id.txt_user_name_up);
        phone = root.findViewById(R.id.txt_user_phone_up);
        job = root.findViewById(R.id.txt_user_job_up);
        card_date = root.findViewById(R.id.txt_user_card_date_up);
        card_date = root.findViewById(R.id.txt_user_card_date_up);
        licence = root.findViewById(R.id.txt_user_licence_up);
        address = root.findViewById(R.id.txt_user_address_up);

        // date = root.findViewById(R.id.txt_user_date_up);
        //address = root.findViewById(R.id.txt_address);
       // carNumber = root.findViewById(R.id.txt_user_car_Num_up);
        //Type = root.findViewById(R.id.txt_user_type_car_up);
        //userJob = root.findViewById(R.id.text_job);
         apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
      //  radioGroup = root.findViewById(R.id.rg_gender);

    }



    public void setDriverData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_driver.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Name.setText("");
                phone.setText("");
                job.setText("");
                card_date.setText("");
                licence.setText("");
                address.setText("");

                Toast.makeText(getContext(), "updated done", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {


                // EditText userName, password, rePassword, firstName, lastName, phoneNo, email, userJob,carNumber,carType,address;
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", Name.getText().toString());
               // map.put("type", Type.getText().toString());
                map.put("phone", phone.getText().toString());
                map.put("job", job.getText().toString());
                map.put("card_date", card_date.getText().toString());
                map.put("licence", licence.getText().toString());
                map.put("address", address.getText().toString());
                //map.put("address", address.getText().toString());
                return map;
            }


        };

        queue.add(request);


    }

    public void setPoliceData() {
        final String url = "https://driverchecker.000webhostapp.com/insert_police.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Name.setText("");
                phone.setText("");
                job.setText("");
                address.setText("");
               // card_date.setText("");
               // licence.setText("");
                //firstName.setText("");
              //  lastName.setText("");
               // password.setText("");
              //  rePassword.setText("");
                //phoneNo.setText("");
                //jobID.setText("");
              //  dgree.setText("");
               // address.setText("");
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
                map.put("user_name", Name.getText().toString());
                map.put("phone", phone.getText().toString());
                map.put("dgree", job.getText().toString());
                map.put("address", address.getText().toString());
//                map.put("phone", phoneNo.getText().toString());
////              map.put("dgree", dgree.getText().toString());
////              map.put("jobID", jobID.getText().toString());
////              map.put("address", address.getText().toString());


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

