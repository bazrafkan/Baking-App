package com.example.bakingapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.utility.Constant;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepDetailsFragment extends Fragment {
    public interface RecipeStepClick {
        void onNextClick();

        void onPreviousClick();
    }

    private Step mStep;
    private TextView mTextViewDescription;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private RecipeStepClick mRecipeStepClick;
    private PlayerView mPlayerView;
    private SimpleExoPlayer player;
    private boolean isVisibleNext = false;
    private boolean isVisiblePrevious = false;

    public static RecipeStepDetailsFragment newInstance(Step mStep) {
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECIPE_STEP_KEY, mStep);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
        showDetails();
    }

    public void setRecipeStepClick(RecipeStepClick mRecipeStepClick) {
        this.mRecipeStepClick = mRecipeStepClick;
    }

    public void setVisibleNext(boolean visibleNext) {
        isVisibleNext = visibleNext;
    }

    public void setVisiblePrevious(boolean visiblePrevious) {
        isVisiblePrevious = visiblePrevious;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_step_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayerView = view.findViewById(R.id.pv_recipe_video);
        mTextViewDescription = view.findViewById(R.id.tv_recipe_step_description);
        mButtonNext = view.findViewById(R.id.bn_next_step);
        mButtonPrevious = view.findViewById(R.id.bn_previous_step);

        mStep = (Step) getArguments().getSerializable(Constant.RECIPE_STEP_KEY);
        showDetails();
    }

    private void showDetails() {
        if (mButtonNext != null) {
            if (isVisibleNext) {
                mButtonNext.setVisibility(View.VISIBLE);
            } else {
                mButtonNext.setVisibility(View.INVISIBLE);
            }

            mButtonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecipeStepClick != null) {
                        mRecipeStepClick.onNextClick();
                    }
                }
            });
        }

        if (mButtonPrevious != null) {
            if (isVisiblePrevious) {
                mButtonPrevious.setVisibility(View.VISIBLE);
            } else {
                mButtonPrevious.setVisibility(View.INVISIBLE);
            }

            mButtonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecipeStepClick != null) {
                        mRecipeStepClick.onPreviousClick();
                    }
                }
            });
        }

        if (mTextViewDescription != null) {
            if (mStep.getDescription() != null && !mStep.getDescription().isEmpty()) {
                mTextViewDescription.setText(mStep.getDescription());
            } else {
                mTextViewDescription.setText("Not available");
            }
        } else {
            hideSystemUi();
        }
        Context context = getContext();
        String CONTENT_URL = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";
        if (mStep.getVideoURL() != null && !mStep.getVideoURL().isEmpty()) {
            CONTENT_URL = mStep.getVideoURL();
        }

        TrackSelector trackSelectorDef = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelectorDef);
        String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));

        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
        Uri uriOfContentUrl = Uri.parse(CONTENT_URL);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(defaultDataSourceFactory).createMediaSource(uriOfContentUrl);

        //TODO add current position to save instance
//        player.seekTo(currentPosition);
        player.setPlayWhenReady(true);
        player.prepare(mediaSource, false, false);

        mPlayerView.setPlayer(player);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.release();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
