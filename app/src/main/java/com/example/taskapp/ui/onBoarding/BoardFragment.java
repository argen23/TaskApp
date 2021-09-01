package com.example.taskapp.ui.onBoarding;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentBoardBinding;
import com.example.taskapp.ui.Prefs;
import com.example.taskapp.ui.animations.DepthPageTransformer;
import com.example.taskapp.ui.animations.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayoutMediator;


public class BoardFragment extends Fragment {
    private FragmentBoardBinding boardBinding;


    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boardBinding = FragmentBoardBinding.inflate(inflater, container, false);
        return boardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 =view.findViewById(R.id.vp_onBoard);
        // set adapter
        BoardAdapter boardAdapter = new BoardAdapter();
        viewPager2.setAdapter(boardAdapter);
        // pager animation
        viewPager2.setPageTransformer(new DepthPageTransformer());
        // set pager
        boardBinding.springDotsIndicator.setViewPager2(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                if (position==2){
                    boardBinding.btnOnBoardSkip.setVisibility(View.INVISIBLE);
                }else {
                    boardBinding.btnOnBoardSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        boardBinding.btnOnBoardSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skip();
            }
        });

    }
    private void skip() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}
