package com.example.todolist_example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist_example.R;
import com.example.todolist_example.data.Task;
import com.example.todolist_example.data.TasksAdapter;
import com.example.todolist_example.data.TasksDB;
import com.example.todolist_example.data.TasksDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    FloatingActionButton addTaskButton;

    private FragmentActivity activityContext;

    RecyclerView recyclerView;

    List<Task> loadedTasks;

    @Override
    public void onAttach(@NonNull Activity activity) {
        activityContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TasksDB tasksDB = TasksDB.getInstance(getActivity());
        TasksDao tasksDao = tasksDB.tasksDao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        addTaskButton = view.findViewById(R.id.add_task_button);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        addTaskButton.setOnClickListener(v -> {
            activityContext.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_main, NewTaskFragment.class, null)
                    .commit();
        });

        tasksDao.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Task>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Task> tasks) {
                        loadedTasks = tasks;
                        TasksAdapter adapter = new TasksAdapter(loadedTasks);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return view;
    }
}