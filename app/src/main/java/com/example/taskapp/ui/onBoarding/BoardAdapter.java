package com.example.taskapp.ui.onBoarding;
import android.app.Activity;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.databinding.ListBoardBinding;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ListBoardBinding binding;

    private int[] titles = new int[]{R.string.title_ob1,R.string.title_ob2,R.string.title_ob3};
    private int [] descriptions = new int[]{R.string.desc_ob1,R.string.desc_ob2,R.string.desc_ob3};
    private int [] image = new int[]{R.drawable.ic_snpt,R.drawable.ic_book,R.drawable.ic_baseline_short_text_24};


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.bind(position);

        if (position==2){
            binding.btnOnBoardGo.setVisibility(View.VISIBLE);
        }else {
            binding.btnOnBoardGo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull ListBoardBinding itemView) {
            super(itemView.getRoot());
        }

        public void bind(int position) {
            binding.tvOnBoardTitle.setText(titles[position]);
            binding.tvOnBoardDesc.setText(descriptions[position]);
            binding.ivOnBoard.setImageResource(image[position]);

            binding.btnOnBoardGo.setText("start");

            binding.btnOnBoardGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController((Activity) v.getContext(), R.id.nav_host_fragment);
                    navController.navigateUp();
                }
            });
        }
    }
}
