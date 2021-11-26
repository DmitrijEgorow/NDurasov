package com.dmitrij.viable.ndurasov.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dmitrij.viable.ndurasov.R;
import com.dmitrij.viable.ndurasov.databinding.FragmentFullCardBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main text card
 */
public class FullCardFragment extends Fragment {

    protected static final int NUM_OF_POINTS = 18;
    private static final String PARAM = "param1";
    private final String TAG = "interchange";


    private FragmentFullCardBinding fragmentFullTextCardBinding;


    private ItemViewModel stepViewModel;
    private PlayerViewModel playerViewModel;
    private FloorViewModel floorViewModel;
    private VideoViewModel videoViewModel;
    protected static AudioPlayer mPlayer;

    private static final String STEP = "param1";
    private static final String ROUTE_NUMBER = "param2";
    private static final String FLOATING_BUTTON = "param3";

    private String step;
    private String routeNumber;
    private String floating_button;
    private int trackID = R.raw.guide1;

    private boolean expanded = true;


    public FullCardFragment() {
        // Required empty public constructor
    }

    public static FullCardFragment newInstance(String param1, String param2, String param3) {
        FullCardFragment fragment = new FullCardFragment();
        Bundle args = new Bundle();
        args.putString(STEP, param1);
        args.putString(ROUTE_NUMBER, param2);
        args.putString(FLOATING_BUTTON, param3);
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

        fragmentFullTextCardBinding = FragmentFullCardBinding.inflate(inflater, container, false);

        switch (step) {
            case "0":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point0);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point0_text);
                trackID = R.raw.guide0m;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_0, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "1":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point1);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point1_text);
                trackID = R.raw.guide1;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_1, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "2":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point2);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point2_text);
                trackID = R.raw.guide2;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_2, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "3":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point3);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point3_text);
                trackID = R.raw.guide3;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_3, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "4":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point4);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point4_text);
                trackID = R.raw.guide4;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_4, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "5":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point5);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point5_text);
                trackID = R.raw.guide5m;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_5, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "6":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point6);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point6_text);
                trackID = R.raw.guide6m;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_6, getContext().getTheme()));

                break;
            case "7":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point7);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point7_text);
                trackID = R.raw.guide7;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_1, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "8":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point8);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point8_text);
                trackID = R.raw.guide8;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_8, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "9":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point9);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point9_text);
                trackID = R.raw.guide9;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_9, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "10":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point10);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point10_text);
                trackID = R.raw.guide10;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_10, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "11":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point11);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point11_text);
                trackID = R.raw.guide11;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_11, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "12":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point12);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point12_text);
                trackID = R.raw.guide12m;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_12, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "13":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point13);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point13_text);
                trackID = R.raw.guide13;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_13, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "14":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point14);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point14_text);
                trackID = R.raw.guide14;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_14, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "15":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point15);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point15_text);
                trackID = R.raw.guide15;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_15, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "16":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point16);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point16_text);
                trackID = R.raw.guide16;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_16, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "17":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point17);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point17_text);
                trackID = R.raw.guide17;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_17, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                break;
            case "18":
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point18);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point18_text);
                trackID = R.raw.guide18;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_18, getContext().getTheme()));
                fragmentFullTextCardBinding.forge3dCardview.setVisibility(View.GONE);
                fragmentFullTextCardBinding.continueButtonLang.setText(R.string.exit);
                break;
            default:
                fragmentFullTextCardBinding.pointTitle.setText(R.string.point1);
                fragmentFullTextCardBinding.fullTextButton.setText(R.string.point1_text);
                trackID = R.raw.guide1;
                fragmentFullTextCardBinding.imagePreview.setImageDrawable(
                        ResourcesCompat.getDrawable(getResources(), R.drawable.preview_1, getContext().getTheme()));
                break;
        }

        fragmentFullTextCardBinding.forge3dButton.setOnClickListener(v -> {
            Log.d("gsonon", step);

            if (step.equals("6")) {
                if (videoViewModel.getProgress().getValue().equals(1)) {
                    videoViewModel.setProgress(0);
                    fragmentFullTextCardBinding.forge3dButton.setBackgroundTintList(
                            ColorStateList.valueOf(
                                    getResources().getColor(
                                            R.color.gray, requireContext().getTheme())
                            ));
                    fragmentFullTextCardBinding.forge3dButton.
                            setIconTint(ColorStateList.valueOf(
                                    getResources().getColor(
                                            R.color.orange, requireContext().getTheme())
                            ));

                } else {
                    if (videoViewModel.getProgress().getValue().equals(0)) {
                        videoViewModel.setProgress(1);
                        fragmentFullTextCardBinding.forge3dButton.
                                setBackgroundTintList(
                                        ColorStateList.valueOf(
                                                getResources().getColor(
                                                        R.color.orange, requireContext().getTheme())
                                        ));
                        fragmentFullTextCardBinding.forge3dButton.
                                setIconTint(ColorStateList.valueOf(
                                        getResources().getColor(
                                                R.color.black, requireContext().getTheme())
                                ));
                    }
                }

            }
        });

        //todo collapse card
        fragmentFullTextCardBinding.playerBottom.textButtonExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded) {
                    int white = getResources().getColor(R.color.white, getContext().getTheme());
                    fragmentFullTextCardBinding.playerBottom.textButtonExpand.setTextColor(white);
                    fragmentFullTextCardBinding.playerBottom.textButtonExpand.setIconTint(ColorStateList.valueOf(white));
                    //fragmentFullTextCardBinding.fullTextButton.setHeight(0);
                    expanded = false;
                } else {
                    int orange = getResources().getColor(R.color.orange, getContext().getTheme());
                    fragmentFullTextCardBinding.playerBottom.textButtonExpand.setTextColor(orange);
                    fragmentFullTextCardBinding.playerBottom.textButtonExpand.setIconTint(ColorStateList.valueOf(orange));
                    //fragmentFullTextCardBinding.fullTextButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    expanded = true;
                }

            }
        });
        fragmentFullTextCardBinding.playerBottom.textButtonExpand.setVisibility(View.GONE);

        return fragmentFullTextCardBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "destroyed!");
        playerViewModel.setProgress(new Pair<>(-1, 0));
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        stepViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        floorViewModel = new ViewModelProvider(requireActivity()).get(FloorViewModel.class);
        videoViewModel = new ViewModelProvider(requireActivity()).get(VideoViewModel.class);

        if (step != null) {
            stepViewModel.select(step);

            if (Integer.parseInt(step) >= 0 && Integer.parseInt(step) <= 10) {
                floorViewModel.select("1");
            } else {
                if (Integer.parseInt(step) > 10 && Integer.parseInt(step) <= 17) {
                    floorViewModel.select("2");
                } else {
                    floorViewModel.select("3");
                }
            }

        } else {
            stepViewModel.select("");
        }
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);


        mPlayer = new AudioPlayer(trackID, rootView.getContext());


        stepViewModel.getSelectedItem().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals(step)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PARAM, s);
                    Navigation.findNavController(getView()).navigate(R.id.action_nav_point_self, bundle);
                }
            }
        });


        rootView.findViewById(R.id.continue_button_lang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString(PARAM, ((Integer.parseInt(step) + 1) % (NUM_OF_POINTS + 1)) + "");
                Navigation.findNavController(v).navigate(R.id.action_nav_point_self, bundle);
                try {
                    mPlayer.pause();
                    mPlayer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        trackID = R.raw.guide1;


        playerViewModel.getProgress().observe(getViewLifecycleOwner(), s -> {
            fragmentFullTextCardBinding.playerBottom.progressBar.setProgress(s.first);
            String s1 = String.format("%02d", (s.first / 1000) / 60) + ":"
                    + String.format("%02d", (s.first / 1000) % 60);
            String s2 = String.format("%02d", (s.second / 1000) / 60) + ":"
                    + String.format("%02d", (s.second / 1000) % 60);
            fragmentFullTextCardBinding.playerBottom.pauseButtonMain.
                    setText(String.format(getString(R.string.timer_counter), s1, s2));
            fragmentFullTextCardBinding.playerBottom.playButtonMain.
                    setText(String.format(getString(R.string.timer_counter), s1, s2));

            if (s.first == -1) {
                playerViewModel.setProgress(new Pair<>(0, s.second));
                fragmentFullTextCardBinding.playerBottom.playButtonMain.setVisibility(View.VISIBLE);
                fragmentFullTextCardBinding.playerBottom.pauseButtonMain.setVisibility(View.INVISIBLE);
            }
        });

        fragmentFullTextCardBinding.playerBottom.playButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimer();
                mPlayer.play(rootView.getContext());
                fragmentFullTextCardBinding.playerBottom.playButtonMain.setVisibility(View.INVISIBLE);
                fragmentFullTextCardBinding.playerBottom.pauseButtonMain.setVisibility(View.VISIBLE);
            }
        });

        fragmentFullTextCardBinding.playerBottom.pauseButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimer();
                mPlayer.pause();
                fragmentFullTextCardBinding.playerBottom.pauseButtonMain.setVisibility(View.INVISIBLE);
                fragmentFullTextCardBinding.playerBottom.playButtonMain.setVisibility(View.VISIBLE);
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
                int i = 0;
                try {
                    i = mPlayer.getDuration().first;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentFullTextCardBinding.playerBottom.progressBar.setProgress(i);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        playerViewModel.setProgress(mPlayer.getDuration());
                    }
                });

                try {
                    Log.d(TAG, "Remaining: " + mPlayer.getDuration().first / 1000);
                    if (mPlayer.getDuration().second - mPlayer.getDuration().first < 2000) {
                        timerTask.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    timerTask.cancel();
                }
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
            try {
                if (mPlayer != null) {
                    return new Pair<>(mPlayer.getCurrentPosition(),
                            mPlayer.getDuration());
                }
            } catch (Exception e) {
                e.printStackTrace();
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

