package com.example.todolist_example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.todolist_example.R;
import com.example.todolist_example.data.Task;
import com.example.todolist_example.data.TasksDB;
import com.example.todolist_example.data.TasksDao;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewTaskFragment extends Fragment {

    ImageButton returnButton;
    Button addTaskButton;
    EditText taskHeaderView;
    EditText taskDescriptionView;

    Disposable disposable;

    private FragmentActivity activityContext;

    @Override
    public void onAttach(@NonNull Activity activity) {
        activityContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        TasksDB tasksDB = TasksDB.getInstance(getActivity());
        TasksDao tasksDao = tasksDB.tasksDao();

        returnButton = view.findViewById(R.id.returnButton);
        addTaskButton = view.findViewById(R.id.addTaskButton);

        taskHeaderView = view.findViewById(R.id.taskHeaderEditText);
        taskDescriptionView = view.findViewById(R.id.taskDescEditText);

        returnButton.setOnClickListener(v -> {
            returnToHomeScreen();
        });

        addTaskButton.setOnClickListener(v -> {
            Task task = readFromUI();

            if (task == null) {
                Toast
                        .makeText(getActivity(), "Укажите название задачи!", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            disposable = tasksDao.insert(task)
                    .subscribeOn(Schedulers.io()).subscribe(this::returnToHomeScreen);
        });


        return view;
    }

    private Task readFromUI() {
        String taskHeader = taskHeaderView.getText().toString();
        String taskDescription = taskDescriptionView.getText().toString();

        if (taskHeader.isEmpty())
            return null;

        return new Task(taskHeader, taskDescription);
    }

    private void returnToHomeScreen() {
        activityContext.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_main, HomeFragment.class, null)
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (disposable != null) disposable.dispose();
    }
}