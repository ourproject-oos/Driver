package com.example.driver;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainAllActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    DrawerLayout drawer;
    Fragment fragment;
    private File f;
    private SharedPreferences sharedPreferences;
    String major = "", policeID, name = "";
    private NavGraph navGraph;
    private View haderView;
    TextView userNameNav, userMajorNav;
    RoundedImageView userImageNav;
    TextView userPoliceID;
    int id;
    AlertDialog alertDialog;
    String PoliceImageUri = "https://driverchecker.000webhostapp.com/police_img.php";
    String DriverImageUri = "https://driverchecker.000webhostapp.com/police_img.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



       

        f = new File("/data/data/" + getPackageName() + "/shared_prefs/" + getString(R.string.shared_preference_usr) + ".xml");
        if (f.exists()) {
            sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_usr), MODE_PRIVATE);
            major = sharedPreferences.getString("major", "");
            name = sharedPreferences.getString("fullName", "user1");


            Toast.makeText(this, major, Toast.LENGTH_SHORT).show();
        }
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        haderView = navigationView.getHeaderView(0);
        userNameNav = haderView.findViewById(R.id.userName_in_nav);
        userMajorNav = haderView.findViewById(R.id.user_email_in_nav);
        userPoliceID = haderView.findViewById(R.id.police_id);
        userImageNav = haderView.findViewById(R.id.img_user_nav);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (major.equals("MANAGER")) {

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_dashboard, R.id.nav_update_delete_user, R.id.nav_logout)
                    .setDrawerLayout(drawer)
                    .build();

            navigationView.bringToFront();
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);

            navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.nav_home);
            navController.setGraph(navGraph);
            userNameNav.setText(name);
            userMajorNav.setText(major);
            userPoliceID.setText("");
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);

        } else if (major.equals("POLICE")) {

            Picasso.get().load(PoliceImageUri).into(userImageNav);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_gallery, R.id.nav_logout)
                    .setDrawerLayout(drawer)
                    .build();

            navigationView.bringToFront();

            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            userNameNav.setText(name);
            userMajorNav.setText(major);
            userPoliceID.setText(policeID);
            navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.nav_gallery);
            navController.setGraph(navGraph);
            navigationView.getMenu().getItem(0).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        } else {

            Picasso.get().load(DriverImageUri).into(userImageNav);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                    R.id.nav_dashboard, R.id.nav_logout)
                    .setDrawerLayout(drawer)
                    .build();

            navigationView.bringToFront();

            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            userNameNav.setText(name);
            userMajorNav.setText("DRIVER");
            userPoliceID.setText("");
            navGraph = navController.getGraph();
            navGraph.setStartDestination(R.id.nav_slideshow);
            navController.setGraph(navGraph);

            navigationView.getMenu().getItem(0).setVisible(false);
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);

        }

        navigationView.getMenu().getItem(5).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
             //   Toast.makeText(MainAllActivity.this, "Error", Toast.LENGTH_SHORT).show();
                if (item.getItemId()== R.id.nav_logout)
                {
                    logout();
                }
                else
                {
                    Toast.makeText(MainAllActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.activity_main_all_drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//
//        if (id == R.id.nav_tools) {
//            Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void logout() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            SharedPreferences sharedPreferences = MainAllActivity.this.
                                    getSharedPreferences("user_sp", MODE_PRIVATE);
                            ///data/data/com.example.driver/shared_prefs/user_sp.xml


                            public void onClick(DialogInterface dialog, int id) {
                                File fileSharedPref =
                                        new File("/data/data/" + getPackageName() + "/shared_prefs/" +
                                                getString(R.string.shared_preference_usr) + ".xml");

                                if (fileSharedPref.exists()) {
                                    fileSharedPref.delete();
                                }

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_all, menu);
        return true;
    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        return super.onOptionsItemSelected(item);
//
//    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
