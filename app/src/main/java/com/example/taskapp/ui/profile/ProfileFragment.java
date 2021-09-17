package com.example.taskapp.ui.profile;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;
import com.example.taskapp.room.Prefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private ImageView ava;
    private Uri uri;
    private Prefs prefs;
    private TextInputEditText et_username, et_number;
    private FloatingActionButton fab;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(requireContext());
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ava = view.findViewById(R.id.avatar_img);
        fab = view.findViewById(R.id.image_fab);

        et_number = view.findViewById(R.id.et_number);
        et_username = view.findViewById(R.id.et_username);
        et_username.setText(prefs.getName());
        et_number.setText(prefs.getNumber());


        fab.setOnClickListener(v -> {
            openGallery();
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        uri = result.getData().getData();
                        prefs.saveImageUri(String.valueOf(uri));
                        ava.setImageURI(uri);

                    }
                }
            });

    public void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        someActivityResultLauncher.launch(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (prefs.getImageUri() != null) uri = Uri.parse(prefs.getImageUri());
        Glide.with(requireContext()).load(uri).circleCrop().into(ava);

    }


    @Override
    public void onStop() {
        super.onStop();
        prefs.saveUserName(et_username.getText().toString());
        prefs.saveNumber(et_number.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(requireContext()).load(uri).circleCrop().into(ava);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_delete) {
            prefs.delete(requireContext());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}