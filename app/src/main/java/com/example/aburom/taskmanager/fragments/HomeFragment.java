package com.example.aburom.taskmanager.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aburom.taskmanager.R;
import com.example.aburom.taskmanager.TaskPageActivity;
import com.example.aburom.taskmanager.adapters.TaskListAdapter;
import com.example.aburom.taskmanager.app.AppController;
import com.example.aburom.taskmanager.models.Task;
import com.example.aburom.taskmanager.utils.API_URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    static ArrayList<Task> homeFragmentData = new ArrayList<>();
    ArrayList<Task> homeFragmentDataShuffled = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    TaskListAdapter taskListAdapter;
    View root;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        homeFragmentData = new ArrayList<>();
        homeFragmentDataShuffled = new ArrayList<>();
        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.fragment_home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayout.VERTICAL, false));
        taskListAdapter = new TaskListAdapter(homeFragmentDataShuffled, getActivity());
        recyclerView.setAdapter(taskListAdapter);
        getTasks();



        return root;
    }

    public void getTasks() {

        showDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URLS.GET_TASKS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hzm", response.toString());
                        //Log.e("hzm", "gooooooooooooootem");

                        if(!homeFragmentData.isEmpty()){
                            homeFragmentData.clear();
                        }
                        JSONArray tasks = null;
                        try {
                            tasks = response.getJSONArray("tasks");

                            for (int i = 0; i < tasks.length(); i++) {

                                JSONObject object = tasks.getJSONObject(i);

                                int id = object.getInt("id");
                                String title = object.getString("title");
                                String summary = object.getString("summary");
                                String description = object.getString("description");

                                String[] dateAndTime = object.getString("date").split(" ");
                                String date = dateAndTime[0];
                                String time = dateAndTime[1];

                                String image = object.getString("image");
                                Double longitude = Double.parseDouble(object.getString("longitude"));
                                Double latitude = Double.parseDouble(object.getString("latitude"));

                                Task task = new Task(id, title, summary, description, time, date, image, longitude, latitude);
                                homeFragmentData.add(task);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        homeFragmentDataShuffled.addAll(homeFragmentData);
                        Collections.shuffle(homeFragmentDataShuffled);
                        TasksFragment.updateTasksFragment();
                        hideDialog();
                        taskListAdapter.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                Toast.makeText(getActivity(), "Failed To Retrieve Data\n Please Check Your Internet Connection ", Toast.LENGTH_LONG).show();
//                Log.e("eeeeeeeeeeeeeeeeeeeeeee",error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retrieving Data...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



}