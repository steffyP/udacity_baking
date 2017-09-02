package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Step;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_STEP;


/**
 * Created by steffy on 02/09/2017.
 */

public class DetailsFragment extends Fragment implements Player.EventListener{

    private Recipe recipe;
    private int stepPos;
    private TextView detailsTextView;
    private View previous;
    private View next;
    private SimpleExoPlayerView playerView;
    private View noVideoView;
    private SimpleExoPlayer exoPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        if (savedInstanceState != null) {
            stepPos = savedInstanceState.getInt(EXTRA_STEP);
        } else {
            stepPos = getArguments().getInt(EXTRA_STEP);
        }
        recipe = getArguments().getParcelable(EXTRA_RECIPE);

        detailsTextView = ((TextView) view.findViewById(R.id.details));

        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);

        playerView = (SimpleExoPlayerView) view.findViewById(R.id.player);

        noVideoView = view.findViewById(R.id.no_video_view);
        initPlayer();

        // this is the landscape layout
        if (next != null) {
            setPortraitView();
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(recipe.getSteps().get(stepPos).getShortDescription());
        return view;

    }
    private void initPlayer() {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            playerView.setPlayer(exoPlayer);
            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            loadVideo();
        }

    }

    private void loadVideo() {
        exoPlayer.setPlayWhenReady(false);
        Step step = recipe.getSteps().get(stepPos);
        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            noVideoView.setVisibility(View.GONE);
            String userAgent = Util.getUserAgent(getActivity(), "BakingMaster");
            Uri mediaUri = Uri.parse(step.getVideoURL());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        } else {
            playerView.setVisibility(View.GONE);
            noVideoView.setVisibility(View.VISIBLE);
        }

    }


    private void setPortraitView() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepPos - 1 >= 0) {
                    stepPos--;
                    updateContent();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepPos + 1 < recipe.getSteps().size()) {
                    stepPos++;
                    updateContent();
                }
            }
        });

        updateContent();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exoPlayer != null) exoPlayer.release();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_STEP, stepPos);

        super.onSaveInstanceState(outState);
    }

    private void updateContent() {
        loadVideo();
        Step step = recipe.getSteps().get(stepPos);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(step.getShortDescription());

        detailsTextView.setText(step.getDescription());
        if (stepPos == 0) {
            previous.setVisibility(View.INVISIBLE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }
        if (stepPos >= recipe.getSteps().size() - 1) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        playerView.setVisibility(View.GONE);
        noVideoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    public void updateContent(int pos) {
        getArguments().putInt(EXTRA_STEP, pos);
        stepPos = pos;
        exoPlayer.stop();
        updateContent();
    }
}
