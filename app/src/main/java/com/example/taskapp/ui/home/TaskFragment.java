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

import com.example.taskapp.R;
import com.example.taskapp.ui.models.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TaskFragment extends Fragment {

    private EditText editText;

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
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                save();
                close();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void save() {
        String text = editText.getText().toString();
        if (text.isEmpty()){
            Toast.makeText(requireContext(),"type task!",Toast.LENGTH_SHORT).show();
            return;
        }
        // date
        long createdAt = System.currentTimeMillis();
        ZonedDateTime dateTime = Instant.ofEpochMilli(createdAt).atZone(ZoneId.of("Asia/Bishkek"));
        String formatted = dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd MMM yyyy"));
        //

        Task task = new Task(text, formatted);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task",task);
        getParentFragmentManager().setFragmentResult("rk_form",bundle);

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigateUp();
    }
}