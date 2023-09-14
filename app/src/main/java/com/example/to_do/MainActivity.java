package com.example.to_do;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText priorityEditText;
    private Button addTaskButton;

    private ArrayList<TaskItem> taskList;
    private TaskListAdapter taskAdapter;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "TodoPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListView = findViewById(R.id.taskListView);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priorityEditText = findViewById(R.id.priorityEditText);
        addTaskButton = findViewById(R.id.addTaskButton);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        taskList = new ArrayList<>();
        taskAdapter = new TaskListAdapter(this, taskList);
        taskListView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String priority = priorityEditText.getText().toString();

                if (!title.isEmpty() && !description.isEmpty() && !priority.isEmpty()) {
                    TaskItem task = new TaskItem(title, description, priority, false);
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                    titleEditText.getText().clear();
                    descriptionEditText.getText().clear();
                    priorityEditText.getText().clear();
                }
            }
        });
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String priority= priorityEditText.getText().toString();
                if (!title.isEmpty() && !description.isEmpty() && !priority.isEmpty()) {
                    TaskItem task = new TaskItem(title, description, priority, false);
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                    titleEditText.getText().clear();
                    descriptionEditText.getText().clear();
                    priorityEditText.getText().clear();

                    // Save the updated task list to SharedPreferences
                    saveTaskListToSharedPreferences();
                }
            }
        });
        loadTaskListFromSharedPreferences();
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskItem task = taskList.get(position);
                openEditTaskActivity(task, position);
            }
        });




    }
    private void saveTaskListToSharedPreferences() {
        // Serialize the taskList to a set of strings
        Set<String> taskSet = new HashSet<>();
        for (TaskItem task : taskList) {
            taskSet.add(task.serializeToString());}
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("taskList", taskSet);
        editor.apply();
    }
    private void loadTaskListFromSharedPreferences() {
        // Load the set of serialized tasks from SharedPreferences
        Set<String> taskSet = sharedPreferences.getStringSet("taskList", null);

        if (taskSet != null) {
            // Deserialize each task and add it to the task list
            for (String serializedTask : taskSet) {
                TaskItem task = TaskItem.deserializeFromString(serializedTask);
                taskList.add(task);
            }

            // Notify the adapter that the data has changed
            taskAdapter.notifyDataSetChanged();
        }
    }
    private void openEditTaskActivity(TaskItem task, int position) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                TaskItem updatedTask = (TaskItem) data.getSerializableExtra("updatedTask");
                int position = data.getIntExtra("position", -1);
                if (position != -1) {

                    taskList.set(position, updatedTask);
                    taskAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}


