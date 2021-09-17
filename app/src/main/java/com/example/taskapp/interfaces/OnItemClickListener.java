package com.example.taskapp.interfaces;

import com.example.taskapp.ui.models.Task;

public interface OnItemClickListener {
    void onClick(int position);
    void onLongClick(int position);

}