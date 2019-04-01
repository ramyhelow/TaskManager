package com.example.aburom.taskmanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aburom.taskmanager.R;
import com.example.aburom.taskmanager.TaskPageActivity;
import com.example.aburom.taskmanager.models.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    ArrayList<Task> data;
    Activity activity;

    public TaskListAdapter(ArrayList<Task> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.task_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.taskId.setText("#" + data.get(position).getId());
        holder.taskName.setText(data.get(position).getTitle());
        holder.taskSummary.setText(data.get(position).getSummary());
        holder.taskTime.setText(data.get(position).getTime().substring(0, 5));
        holder.taskDate.setText(data.get(position).getDate());
        Picasso.get().load(data.get(position).getImageUrl())
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.taskImage);

        holder.taskCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TaskPageActivity.class);
                intent.putExtra("task", data.get(position));
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView taskCardView;
        public ImageView taskImage;
        public TextView taskId;
        public TextView taskName;
        public TextView taskSummary;
        public TextView taskTime;
        public TextView taskDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskCardView = itemView.findViewById(R.id.task_cardview);
            taskImage = itemView.findViewById(R.id.item_task_image);
            taskId = itemView.findViewById(R.id.item_task_id);
            taskName = itemView.findViewById(R.id.item_task_name);
            taskSummary = itemView.findViewById(R.id.item_task_summary);
            taskTime = itemView.findViewById(R.id.item_task_time);
            taskDate = itemView.findViewById(R.id.item_task_date);
        }
    }
}
