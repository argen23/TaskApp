package com.example.taskapp.ui.home;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.ui.models.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TaskFragment extends Fragment {

    private EditText editText;

    private Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.et_todo);
        task = (Task) requireArguments().getSerializable("updateTask");
        if (task != null)editText.setText(task.getTitle());

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void save() {
        Bundle bundle = new Bundle();


        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "type task!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (task == null) {
            // date
            long createdAt = System.currentTimeMillis();
            ZonedDateTime dateTime = Instant.ofEpochMilli(createdAt).atZone(ZoneId.of("Asia/Bishkek"));
            String formatted = dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd MMM yyyy"));
            //date
            task = new Task(text, formatted);
            App.getAppDataBase().taskDao().insert(task);
        }else{
            task.setTitle(text);
            App.getAppDataBase().taskDao().editItem(task);

        }


        bundle.putSerializable("task", task);
        getParentFragmentManager().setFragmentResult("rk_form", bundle);
        close();

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}