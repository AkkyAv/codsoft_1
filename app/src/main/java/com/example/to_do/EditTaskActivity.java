package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDescription;
    private EditText editPriority;
    private Button saveButton;
    private Button cancelButton;
    private TaskItem task;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editPriority = findViewById(R.id.editPriority);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);


        Intent intent = getIntent();
        if (intent != null) {
            task = (TaskItem) intent.getSerializableExtra("task");
            position = intent.getIntExtra("position", -1);
            if (task != null) {
                editTitle.setText(task.getTitle());
                editDescription.setText(task.getDescription());

            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskItem updatedTask = new TaskItem(
                        editTitle.getText().toString(),
                        editDescription.getText().toString(),
                        editPriority.getText().toString(),true
                );
                returnEditedTask(updatedTask);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); }
        });
    }

    private void returnEditedTask(TaskItem updatedTask) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updatedTask", updatedTask);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
