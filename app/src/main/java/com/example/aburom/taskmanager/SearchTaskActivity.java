package com.example.aburom.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aburom.taskmanager.adapters.TaskListAdapter;
import com.example.aburom.taskmanager.app.AppController;
import com.example.aburom.taskmanager.models.Task;
import com.example.aburom.taskmanager.utils.API_URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchTaskActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ArrayList<Task> data = new ArrayList<>();
    TextView toolbarTitle, numberOfSearchResults;
    RecyclerView recyclerView;
    TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_task);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        getSearchResults(query);
        toolbarTitle = findViewById(R.id.search_toolbar_task_title);
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("");
        toolbarTitle.setText("Search Results For: \"" + query + "\"");
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.searchResultsTaskRecyclerView);
        taskListAdapter = new TaskListAdapter(data,SearchTaskActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchTaskActivity.this,LinearLayout.VERTICAL,false));
        recyclerView.setAdapter(taskListAdapter);

        numberOfSearchResults = findViewById(R.id.numberOfSearchResults);
    }

    public void getSearchResults(String title){
        showDialog();

        Map<String, String> params = new HashMap<String, String>();
        params.put("title", title);

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URLS.SEARCH_TASK, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("rmy", response.toString());
                        //Log.e("hzm", "gooooooooooooootem");

                        if(!data.isEmpty()){
                            data.clear();
                        }
                        JSONObject tasks = null;

                        try {
                            tasks = response.getJSONObject("tasks");
                            //Log.e("searchedlength",tasks.length()+"");
                           // for (int i = 0; i < tasks.length(); i++) {

                                numberOfSearchResults.setText("No. Results : 1");

                               // JSONObject object = tasks.getJSONObject(i);

                                int id = tasks.getInt("id");
                                String title = tasks.getString("title");
                                String summary = tasks.getString("summary");
                                String description = tasks.getString("description");

                                String[] dateAndTime = tasks.getString("date").split(" ");
                                String date = dateAndTime[0];
                                String time = dateAndTime[1];

                                String image = tasks.getString("image");
                                Double longitude = Double.parseDouble(tasks.getString("longitude"));
                                Double latitude = Double.parseDouble(tasks.getString("latitude"));

                                Task task = new Task(id, title, summary, description, time, date, image, longitude, latitude);
                                data.add(task);
                           // }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SearchTaskActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        hideDialog();
                        taskListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(taskListAdapter);
                      //  Toast.makeText(SearchTaskActivity.this, data.size()+"  f", Toast.LENGTH_SHORT).show();
                        //custom adapter
                        //HomeFragment.updateHomeFragment();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                Toast.makeText(SearchTaskActivity.this, "Failed To Retrieve Data\n Please Check Your Internet Connection ", Toast.LENGTH_LONG).show();
//                Log.e("eeeeeeeeeeeeeeeeeeeeeee",error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void showDialog() {
        progressDialog = new ProgressDialog(SearchTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retrieving Data...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
