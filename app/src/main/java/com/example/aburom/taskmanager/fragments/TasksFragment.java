package com.example.aburom.taskmanager.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aburom.taskmanager.R;
import com.example.aburom.taskmanager.adapters.TaskListAdapter;
import com.example.aburom.taskmanager.models.Task;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TasksFragment extends Fragment {

    //static ArrayList<Task> tasksFragmentData = new ArrayList<>();
    //ProgressDialog progressDialog;
    RecyclerView recyclerView;
    static TaskListAdapter taskListAdapter;
    View root;

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tasks, container, false);
        recyclerView = root.findViewById(R.id.fragment_tasks_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayout.VERTICAL, false));
        taskListAdapter = new TaskListAdapter(HomeFragment.homeFragmentData, getActivity());
        recyclerView.setAdapter(taskListAdapter);
        //getTasks();



        return root;
    }



    public static void updateTasksFragment(){
        taskListAdapter.notifyDataSetChanged();
    }


//    public void getTasks() {
//
//        showDialog();
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URLS.GET_TASKS, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("hzm", response.toString());
//                        //Log.e("hzm", "gooooooooooooootem");
//
//
//                        JSONArray tasks = null;
//                        try {
//                            tasks = response.getJSONArray("tasks");
//
//                            for (int i = 0; i < tasks.length(); i++) {
//
//                                JSONObject object = tasks.getJSONObject(i);
//
//                                int id = object.getInt("id");
//                                String title = object.getString("title");
//                                String summary = object.getString("summary");
//                                String description = object.getString("description");
//
//                                String[] dateAndTime = object.getString("date").split(" ");
//                                String date = dateAndTime[0];
//                                String time = dateAndTime[1];
//
//                                String image = object.getString("image");
//                                Double longitude = Double.parseDouble(object.getString("longitude"));
//                                Double latitude = Double.parseDouble(object.getString("latitude"));
//
//                                Task task = new Task(id, title, summary, description, time, date, image, longitude, latitude);
//                                tasksFragmentData.add(task);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                        hideDialog();
//                        //custom adapter
//                        taskListAdapter.notifyDataSetChanged();
//                        //HomeFragment.updateHomeFragment();
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                  Log.e("VolleyError", error.toString());
//                Toast.makeText(getActivity(), "Failed To Retrieve Data\nCheck Your Internet Connection ", Toast.LENGTH_LONG).show();
////                Log.e("eeeeeeeeeeeeeeeeeeeeeee",error.getMessage());
//                hideDialog();
//            }
//        });
//
//        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//
//    }
//
//
//    public void showDialog() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading tasksFragmentData...");
//        progressDialog.show();
//    }
//
//    public void hideDialog() {
//        if (progressDialog.isShowing())
//            progressDialog.dismiss();
//    }

}