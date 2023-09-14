package com.example.to_do;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;
import java.util.ArrayList;

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TaskItem> taskList;

    public TaskListAdapter(Context context, ArrayList<TaskItem> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        // Get the task item at the current position
        final TaskItem task = taskList.get(position);

        // Find the views within the task_item layout
        TextView taskTextView = convertView.findViewById(R.id.taskTextView);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        // Set the text for the task TextView
        taskTextView.setText("Title: " + task.getTitle() + "\nDescription: " + task.getDescription() + "\nPriority: " + task.getPriority());

        // Set the checkbox state based on the completion status of the task
        checkBox.setChecked(task.isCompleted());

        // Set an OnClickListener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
