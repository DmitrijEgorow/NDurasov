package com.dmitrij.viable.ndurasov.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmitrij.viable.ndurasov.R;
import com.dmitrij.viable.ndurasov.databinding.FragmentLangCardBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Language selection
 */
public class LangCardFragment extends Fragment {

    private final String TAG = "language_fragment";

    private FragmentLangCardBinding fragmentLangCardBinding;
    private ItemViewModel stepViewModel;

    private CustomAdapter mAdapter;
    private List<Pair<String, Integer>> languages = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private static final String STEP = "param1";
    private static final String ROUTE_NUMBER = "param2";
    private static final String FLOATING_BUTTON = "param3";

    private String step;
    private String routeNumber;
    private String floating_button;


    public LangCardFragment() {
        // Required empty public constructor
    }

    /**
     * factory method for the very first fragment
     * @param step varies from 0 to total number of points.
     * @param routeNum route.
     * @return A new instance of fragment LangCard.
     */
    public static LangCardFragment newInstance(String step, String routeNum, String button) {
        LangCardFragment fragment = new LangCardFragment();
        Bundle args = new Bundle();
        args.putString(STEP, step);
        args.putString(ROUTE_NUMBER, routeNum);
        args.putString(FLOATING_BUTTON, button);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getString(STEP);
            routeNumber = getArguments().getString(ROUTE_NUMBER);
            floating_button = getArguments().getString(FLOATING_BUTTON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragmentLangCardBinding = FragmentLangCardBinding.inflate(inflater, container, false);

        //Initializing list of possible languages
        languages.add(new Pair<>("ru", R.drawable.ic_flag_ru));
        languages.add(new Pair<>("en", R.drawable.ic_flag_en));
        languages.add(new Pair<>("de", R.drawable.ic_flag_de));
        languages.add(new Pair<>("zh", R.drawable.ic_flag_cn));
        languages.add(new Pair<>("es", R.drawable.ic_flag_es));
        languages.add(new Pair<>("it", R.drawable.ic_flag_it));

        return fragmentLangCardBinding.getRoot();

    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);


        stepViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        stepViewModel.select("");

        // RecyclerView
        recyclerView = (RecyclerView) fragmentLangCardBinding.recyclerViewLang;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CustomAdapter(languages);
        recyclerView.setAdapter(mAdapter);


        rootView.findViewById(R.id.continue_button_lang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_lang_to_nav_tutorial);
            }
        });
    }

    private class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView title;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            title = (ImageView) itemView.findViewById(R.id.image_lang);
            itemView.setOnClickListener(this);
        }

        public void bindModel(Pair<String, Integer> item) {
            title.setImageResource(item.second);
        }

        @Override
        public void onClick(View view) {
            Intent refresh = new Intent(getActivity(), MainActivity.class);
            String currentLanguage = languages.get(getLayoutPosition()).first;

            Log.d(TAG, "clicked " + currentLanguage + " " + getLayoutPosition());

            MainActivity.setLocale(getActivity(), currentLanguage);
            startActivity(refresh);
            getActivity().finish();

        }
    }


    private class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {
        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        private List<Pair<String, Integer>> mItems;

        public List<Pair<String, Integer>> getmItems() {
            return mItems;
        }

        public CustomAdapter(List<Pair<String, Integer>> mItems) {
            this.mItems = mItems;
        }

        @NonNull
        @Override
        public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, viewType + " ");
            return new CustomHolder(getLayoutInflater().inflate(R.layout.lang_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomHolder holder, final int position) {
            holder.bindModel(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            if (languages == null) {
                return (0);
            }
            return (languages.size());
        }
    }


}

