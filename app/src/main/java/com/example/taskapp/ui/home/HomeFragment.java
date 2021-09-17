package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentHomeBinding;
import com.example.taskapp.interfaces.OnItemClickListener;
import com.example.taskapp.ui.models.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter taskAdapter;
    private Task task;
    private boolean isChanged = false;
    private int position;
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        taskAdapter = new TaskAdapter();
        taskAdapter.addItems(App.getAppDataBase().taskDao().getAll());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(v -> {
            isChanged = false;
            openTaskFragment(null);
        });
        getParentFragmentManager().setFragmentResultListener("rk_form", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Task task = (Task) result.getSerializable("task");
                Log.e("Home", "task = " + task.getTitle());

                if (isChanged) taskAdapter.updateItem(task, position);
                else taskAdapter.addItem(task);

            }
        });
        initRv();
    }

    private void initRv() {
        binding.homeRv.setAdapter(taskAdapter);
        taskAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(int position) {
                Task task = taskAdapter.getItem(position);
                openTaskFragment(task);
                isChanged = true;
                HomeFragment.this.position = position;
            }

            @Override
            public void onLongClick(int position) {
                alertSs(position);
            }

            private void alertSs(int position) {
                new AlertDialog.Builder(getContext()).setTitle("Удаление")
                        .setMessage("Вы точно хотите удалить?")
                        .setNegativeButton("нет", null)
                        .setPositiveButton("да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                task = taskAdapter.getItem(position);
                                App.getAppDataBase().taskDao().deleteTask(task);
                                taskAdapter.removeItem(position);
                                Toast.makeText(requireContext(), "Delete", Toast.LENGTH_LONG).show();
                            }
                        }).show();

            }
        });
    }

    private void openTaskFragment(Task task) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("updateTask", task);
        navController.navigate(R.id.taskFragment, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.it_sort) {
            taskAdapter.setList(App.getAppDataBase().taskDao().sortAll());
            binding.homeRv.setAdapter(taskAdapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}