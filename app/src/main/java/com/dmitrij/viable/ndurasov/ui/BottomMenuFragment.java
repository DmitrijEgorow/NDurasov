package com.dmitrij.viable.ndurasov.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dmitrij.viable.ndurasov.R;
import com.google.android.material.button.MaterialButton;

/**
 * Floor selection
 */
public class BottomMenuFragment extends Fragment implements View.OnClickListener {

    private ItemViewModel viewModel;
    private FloorViewModel floorViewModel;
    private MaterialButton button1, button2, button3;

    public BottomMenuFragment() {
        // Required empty public constructor
    }

    public static BottomMenuFragment newInstance(String param1, String param2) {
        BottomMenuFragment fragment = new BottomMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_floor_card, container, false);
        rootView.findViewById(R.id.change_lang_main).setOnClickListener(this);
        button1 = rootView.findViewById(R.id.button_floor_1);
        button1.setOnClickListener(this);
        button2 = rootView.findViewById(R.id.button_floor_2);
        button2.setOnClickListener(this);
        button3 = rootView.findViewById(R.id.button_floor_3);
        button3.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        switch (v.getId()) {
            case R.id.change_lang_main:
                startActivity(refresh);
                getActivity().finish();
                break;
            case R.id.button_floor_1:
                floorViewModel.select("1");
                break;
            case R.id.button_floor_2:
                floorViewModel.select("2");
                break;
            case R.id.button_floor_3:
                floorViewModel.select("3");
                break;
        }
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            Log.d("gsonon", item);
            if (!item.equals("")) {
                if (rootView.getVisibility() == View.INVISIBLE) {
                    Navigation.findNavController(rootView).navigate(R.id.action_nav_card_self);
                }
            } else {
                rootView.setVisibility(View.INVISIBLE);
            }
        });

        floorViewModel = new ViewModelProvider(requireActivity()).get(FloorViewModel.class);
        floorViewModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
            Log.d("gsonon_floor", item);
            switch (item) {
                case "1":
                    setFloor1();
                    break;
                case "2":
                    setFloor2();
                    break;
                case "3":
                    setFloor3();
                    break;
                default:
                    setFloor1();
                    break;
            }
        });
    }

    private void setFloor1() {
        button1.setTextColor(getResources().getColor(R.color.orange));
        button2.setTextColor(getResources().getColor(R.color.white));
        button3.setTextColor(getResources().getColor(R.color.white));
    }

    private void setFloor2() {
        button1.setTextColor(getResources().getColor(R.color.white));
        button2.setTextColor(getResources().getColor(R.color.orange));
        button3.setTextColor(getResources().getColor(R.color.white));
    }

    private void setFloor3() {
        button1.setTextColor(getResources().getColor(R.color.white));
        button2.setTextColor(getResources().getColor(R.color.white));
        button3.setTextColor(getResources().getColor(R.color.orange));
    }
}