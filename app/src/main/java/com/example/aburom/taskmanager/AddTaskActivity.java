package com.example.aburom.taskmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.aburom.taskmanager.app.AppController;
import com.example.aburom.taskmanager.models.Task;
import com.example.aburom.taskmanager.utils.API_URLS;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    Button addTaskButton;
    EditText addTaskTitle;
    EditText addTaskSummary;
    EditText addTaskDescription;
    EditText addTaskImageURL;

    TextInputLayout addTaskTitleInputLayout;
    TextInputLayout addTaskSummaryInputLayout;
    TextInputLayout addTaskDescriptionInputLayout;
    TextInputLayout addTaskImageUrlInputLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        progressDialog = new ProgressDialog(AddTaskActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_back_button);
        setSupportActionBar(toolbar);

        addTaskButton = findViewById(R.id.add_task_button);
        addTaskTitle = findViewById(R.id.add_task_title);
        addTaskSummary = findViewById(R.id.add_task_summary);
        addTaskDescription = findViewById(R.id.add_task_description);
        addTaskImageURL = findViewById(R.id.add_task_imageurl);

        addTaskTitleInputLayout = findViewById(R.id.add_task_title_inputlayout);
        addTaskSummaryInputLayout = findViewById(R.id.add_task_summary_inputlayout);
        addTaskDescriptionInputLayout = findViewById(R.id.add_task_description_inputlayout);
        addTaskImageUrlInputLayout = findViewById(R.id.add_task_imageurl_inputlayout);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Intent intent = new Intent(PermissionActivity.this,MainActivity.class);
                        //startActivity(intent);
                        //readContacts();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Intent intent = new Intent(PermissionActivity.this,MainActivity.class);
                        //startActivity(intent);
                        //readContacts();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


        addTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String title = addTaskTitle.getText().toString();
                String summary = addTaskSummary.getText().toString();
                String description = addTaskDescription.getText().toString();
                String imageUrl = addTaskImageURL.getText().toString();

                boolean validatedTitle = true;
                boolean validatedSummary = true;
                boolean validatedDescription = true;
                boolean validatedImageUrl = true;

                if (title.isEmpty()) {
                    validatedTitle = false;
                    addTaskTitleInputLayout.setBoxStrokeColor(Color.parseColor("red"));
                    //addTaskTitle.setBackgroundResource(R.drawable.edittext_invalid);
                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(addTaskTitleInputLayout);
                } else {
                    addTaskTitleInputLayout.setBoxStrokeColor(0);
                    //addTaskTitle.setBackgroundResource(0);
                }
                if(summary.length()>100){
                    validatedSummary = false;
                    Toast.makeText(AddTaskActivity.this, "Description Must Be Less Than 100 Characters", Toast.LENGTH_SHORT).show();
                }
                if (summary.isEmpty()) {
                    validatedSummary = false;
                    addTaskSummaryInputLayout.setBoxStrokeColor(Color.parseColor("red"));
                    //addTaskSummary.setBackgroundResource(R.drawable.edittext_invalid);
                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(addTaskSummaryInputLayout);
                } else {
                    //addTaskSummary.setTextColor(Color.parseColor("red"));
                    addTaskSummaryInputLayout.setBoxStrokeColor(0);
                }
                if (description.isEmpty()) {
                    validatedDescription = false;
                    addTaskDescriptionInputLayout.setBoxStrokeColor(Color.parseColor("red"));
                    //addTaskDescription.setBackgroundResource(R.drawable.edittext_invalid);
                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(addTaskDescriptionInputLayout);
                } else {
                    //addTaskDescription.setTextColor(Color.parseColor("red"));
                    addTaskDescriptionInputLayout.setBoxStrokeColor(0);
                }
                if (imageUrl.isEmpty()) {
                    validatedImageUrl = false;
                    addTaskImageUrlInputLayout.setBoxStrokeColor(Color.parseColor("red"));
                    //addTaskImageURL.setBackgroundResource(R.drawable.edittext_invalid);
                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(addTaskImageUrlInputLayout);
                } else {
                    //addTaskImageURL.setTextColor(Color.parseColor("red"));
                    addTaskDescriptionInputLayout.setBoxStrokeColor(0);
                }

                boolean taskValid = validatedTitle && validatedSummary && validatedDescription && validatedImageUrl;

                if (taskValid)
                    uploadTask(title, summary, description, imageUrl);
                else{
                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(addTaskButton);
                    Toast.makeText(AddTaskActivity.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(AddTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adding Task...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void uploadTask(String title, String summary, String description, String imageUrl) {
        showDialog();
        double longitude = 0;
        double latitude = 0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            } else {
                Log.e("locationnnnnnnnnnnnnn", "location is nullllllllllll");

            }
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("title", title);
        params.put("summary", summary);
        params.put("description", description);
        params.put("image", imageUrl);
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        Log.e("ramy", "Latitude" + String.valueOf(latitude));
        Log.e("ramy", "Longitude" + String.valueOf(longitude));

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URLS.ADD_TASK, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hzm", response.toString());
                        hideDialog();
                        finish();
                        Toast.makeText(AddTaskActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                Toast.makeText(AddTaskActivity.this, "Failed To Retrieve Data\n Please Check Your Internet Connection ", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        return;
    }

}


