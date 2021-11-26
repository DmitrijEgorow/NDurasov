package com.dmitrij.viable.ndurasov.ui;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.dmitrij.viable.ndurasov.R;
import com.dmitrij.viable.ndurasov.databinding.FragmentDescriptionCardBinding;
import com.dmitrij.viable.ndurasov.databinding.FragmentFullCardBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Quick Tutorial before the tour
 */
public class TutorialFragment extends Fragment {

    private final String TAG = "interchange";

    private FragmentDescriptionCardBinding fragmentDescriptionCardBinding;
    private FragmentFullCardBinding fragmentFullTextCardBinding;


    private ItemViewModel stepViewModel;
    private PlayerViewModel playerViewModel;
    private static AudioPlayer mPlayer;

    private int trackID = R.raw.guide1;

    public TutorialFragment() {
        // Required empty public constructor
    }

    public static TutorialFragment newInstance(String param1, String param2, String param3) {
        TutorialFragment fragment = new TutorialFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDescriptionCardBinding = FragmentDescriptionCardBinding.inflate(inflater, container, false);
        return fragmentDescriptionCardBinding.getRoot();
    }


    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        stepViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        stepViewModel.select("");
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);

        mPlayer = new AudioPlayer(trackID, rootView.getContext());


        rootView.findViewById(R.id.continue_button_lang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("param1", "0");
                Navigation.findNavController(v).navigate(R.id.action_nav_tutorial_to_nav_point, bundle);
            }
        });


    }

    public void runTimer() {
        Pair<Integer, Integer> timer;
        timer = mPlayer.getDuration();

        fragmentFullTextCardBinding.playerBottom.progressBar.setMax(timer.second);
        Timer timerTask = new Timer();
        timerTask.schedule(new TimerTask() {
            @Override
            public void run() {
                int i = mPlayer.getDuration().first;
                fragmentFullTextCardBinding.playerBottom.progressBar.setProgress(i);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        playerViewModel.setProgress(mPlayer.getDuration());
                    }
                });

                if (mPlayer.getDuration().second - mPlayer.getDuration().first < 2000) {
                    timerTask.cancel();
                }


                Log.d(TAG, "Position: " + mPlayer.getDuration().first / 1000);
            }
        }, 0, 200);
    }

    class AudioPlayer {
        private MediaPlayer mPlayer;
        private int track;
        public PlayerViewModel viewModel;
        private boolean paused = false;

        /**
         * @param trackId R.raw.guide1.wav
         */
        public AudioPlayer(int trackId, Context context) {
            track = trackId;
            if (viewModel == null) {
                viewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
            }
            // viewModel.selectTrack(trackId);
            mPlayer = MediaPlayer.create(context, track);
            viewModel.setProgress(new Pair<>(0, mPlayer.getDuration()));

            mPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "complete" + " ");
                paused = false;
                viewModel.setProgress(new Pair<>(-1, mPlayer.getDuration()));
            });

        }

        public void play(Context context) {
            if (mPlayer != null) {
                mPlayer.start();
            }
        }

        public Pair<Integer, Integer> getDuration() {
            if (mPlayer != null) {
                return new Pair<>(mPlayer.getCurrentPosition(),
                        mPlayer.getDuration());
            }
            return null;
        }

        public void pause() {
            if (mPlayer != null) {
                mPlayer.pause();
                paused = true;
            }
        }

        public void stop() {
            if (mPlayer != null) {
                mPlayer.release();
                paused = false;
            }
        }
    }

}

