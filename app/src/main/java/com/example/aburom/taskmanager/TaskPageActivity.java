package com.example.aburom.taskmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.aburom.taskmanager.app.AppController;
import com.example.aburom.taskmanager.models.Task;
import com.example.aburom.taskmanager.utils.API_URLS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TaskPageActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog progressDialog;
    private GoogleMap mMap;
    Task task;

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
        setContentView(R.layout.activity_task_page);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_back_button);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");

        ImageView taskpage_image = findViewById(R.id.taskpage_image);
        TextView taskpage_name = findViewById(R.id.taskpage_name);
        TextView taskpage_description = findViewById(R.id.taskpage_description);
        TextView taskpage_id = findViewById(R.id.taskpage_id);
        TextView taskpage_date = findViewById(R.id.taskpage_date);
        TextView taskpage_time = findViewById(R.id.taskpage_time);
        TextView toolbar_task_title = findViewById(R.id.toolbar_task_title);

        taskpage_date.setText(task.getDate());
        taskpage_time.setText(task.getTime());
        taskpage_description.setText(task.getDescription());
        taskpage_id.setText("#" + task.getId());
        taskpage_name.setText(task.getTitle());
        toolbar_task_title.setText(task.getTitle());
        Picasso.get().load(task.getImageUrl()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(taskpage_image);

        Button deleteButton = findViewById(R.id.delete_task_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TaskPageActivity.this);
                alertDialogBuilder.setTitle("Are You Sure?");
                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dbHelper.deleteNews(news.getId());
                        deleteTask(task.getId());

                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void deleteTask(final int id){
            showDialog();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URLS.DELETE_TASK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("d", response);
                            MainActivity.refreshTabs();
                            Toast.makeText(TaskPageActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                            hideDialog();
                            //onBackPressed();
                            Intent intent = new Intent(TaskPageActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("hzm", error.getMessage());
                            Toast.makeText(TaskPageActivity.this, "Failed To Retrieve Data\n Please Check Your Internet Connection ", Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("task_id", String.valueOf(id));
                    return map;
                }
            };

            AppController.getInstance().addToRequestQueue(stringRequest);

    }

    public void showDialog() {
        progressDialog = new ProgressDialog(TaskPageActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting Task...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(task.getLatitude(), task.getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title("Task Created"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
