package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Step;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Created by steffy on 01/09/2017.
 */

public class StepDetailsActivity extends AppCompatActivity implements Player.EventListener {
    public static final String EXTRA_STEP = "extra_step";

    private Step step;
    private Recipe recipe;
    private View next;
    private View previous;
    private int stepPos;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    private View noVideoView;
    private TextView detailsTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            stepPos = savedInstanceState.getInt(EXTRA_STEP);
        } else {
            stepPos = getIntent().getExtras().getInt(EXTRA_STEP);
        }
        recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);

        step = recipe.getSteps().get(stepPos);

        detailsTextView = ((TextView) findViewById(R.id.details));

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        playerView = (SimpleExoPlayerView) findViewById(R.id.player);

        noVideoView = findViewById(R.id.no_video_view);
        initPlayer();

        // this is the landscape layout
        if (next != null) {
            setPortraitView();
        }

        getSupportActionBar().setTitle(step.getShortDescription());

    }


    @Override
    public Intent getParentActivityIntent() {
        Intent intent = super.getParentActivityIntent();
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }

    private void initPlayer() {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            loadVideo();
        }

    }

    private void loadVideo() {
        exoPlayer.setPlayWhenReady(false);
        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            noVideoView.setVisibility(View.GONE);
            String userAgent = Util.getUserAgent(this, "BakingMaster");
            Uri mediaUri = Uri.parse(step.getVideoURL());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
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
                    step = recipe.getSteps().get(stepPos);
                    updateContent();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepPos + 1 < recipe.getSteps().size()) {
                    stepPos++;
                    step = recipe.getSteps().get(stepPos);
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
            exoPlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_STEP, stepPos);

        super.onSaveInstanceState(outState);
    }

    private void updateContent() {
        loadVideo();

        getSupportActionBar().setTitle(step.getShortDescription());

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

}
