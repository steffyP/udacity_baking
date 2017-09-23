package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.IngredientAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.view.View.GONE;
import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_POSITION;


/**
 * Created by steffy on 02/09/2017.
 */

public class DetailsFragment extends Fragment implements Player.EventListener, IngredientAdapter.IngredientCheckCallback {

    private static final String EXTRA_PLAYER_POSITION = "extra_player_position";
    private Recipe recipe;
    private int position;
    private int stepPos;
    private TextView detailsTextView;
    private View previous;
    private View next;
    private SimpleExoPlayerView playerView;
    private View noVideoView;
    private SimpleExoPlayer exoPlayer;
    private ImageView thumbNailImageView;
    private RecyclerView recyclerView;
    private long positionInMillis;
    private View detailsLayout;
    private View playerLayout;
    private View ingredientLayout;
    private TextView servingsTextView;
    private boolean isPlayerFullScreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(EXTRA_POSITION);
            positionInMillis = savedInstanceState.getLong(EXTRA_PLAYER_POSITION, 0);
        } else {
            position = getArguments().getInt(EXTRA_POSITION);
            positionInMillis = 0;
        }

        recipe = getArguments().getParcelable(EXTRA_RECIPE);
        detailsTextView = ((TextView) view.findViewById(R.id.details));
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);

        playerView = (SimpleExoPlayerView) view.findViewById(R.id.player);
        noVideoView = view.findViewById(R.id.no_video_view);
        thumbNailImageView = view.findViewById(R.id.thumbnail);

        recyclerView = view.findViewById(R.id.ingredients);


        detailsLayout = view.findViewById(R.id.details_layout);
        playerLayout = view.findViewById(R.id.player_layout);
        ingredientLayout = view.findViewById(R.id.ingredient_layout);

        servingsTextView = ((TextView) view.findViewById(R.id.servings));

        initButtonListener();

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            if (getResources().getInteger(R.integer.sw_600) == 1) {
                // tablet is landscape, so it shows both layouts
                isPlayerFullScreen = false;
            } else {
                isPlayerFullScreen = true;
            }
        } else {
            isPlayerFullScreen = false;
        }

        if (position == 0) {
            updateIngredientView();
        } else {
            stepPos = position - 1;
            updateContent();
        }

        return view;

    }

    private void updateIngredientView() {
        previous.setVisibility(GONE);

        if (!isPlayerFullScreen) {
            detailsLayout.setVisibility(GONE);
            playerLayout.setVisibility(GONE);
        } else {
            playerView.setVisibility(GONE);
            noVideoView.setVisibility(GONE);
            ingredientLayout.setVisibility(View.VISIBLE);
        }
        if (exoPlayer != null) {
            exoPlayer.stop();
            positionInMillis = 0;
        }
        ingredientLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new IngredientAdapter(getActivity(), recipe.getIngredients(), this));

        if (recipe.getServings() != 0) {
            servingsTextView.setText(getResources().getString(R.string.servings, recipe.getServings()));
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());

    }

    private void updateStepView() {
        updatePlayer();

        if (!isPlayerFullScreen) {
            detailsLayout.setVisibility(View.VISIBLE);
            playerLayout.setVisibility(View.VISIBLE);
            ingredientLayout.setVisibility(GONE);
            previous.setVisibility(View.VISIBLE);
        } else {
            ingredientLayout.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getSteps().get(stepPos).getShortDescription());
    }

    private void updatePlayer() {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            playerView.setPlayer(exoPlayer);
            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

        } else {
            exoPlayer.stop();
        }

    }

    private void loadVideo() {
        exoPlayer.setPlayWhenReady(false);
        Step step = recipe.getSteps().get(stepPos);
        if (step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            playerView.setVisibility(View.VISIBLE);
            noVideoView.setVisibility(GONE);
            String userAgent = Util.getUserAgent(getActivity(), "BakingMaster");
            Uri mediaUri = Uri.parse(step.getVideoURL());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource, false, true);
            exoPlayer.seekTo(positionInMillis);
            exoPlayer.setPlayWhenReady(true);
        } else {
            playerView.setVisibility(GONE);
            noVideoView.setVisibility(View.VISIBLE);
        }

    }


    private void initButtonListener() {
        if (previous != null)
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0) {
                        position--;
                        positionInMillis = 0;
                        updateContent();
                    }
                }
            });

        if (next != null)
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position + 1 <= recipe.getSteps().size()) {
                        position++;
                        positionInMillis = 0;
                        updateContent();
                    }
                }
            });
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
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) exoPlayer.release();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_POSITION, position);
        if (exoPlayer != null)
            outState.putLong(EXTRA_PLAYER_POSITION, exoPlayer.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    private void updateContent() {
        stepPos = position - 1;
        if (stepPos < 0) {
            updateIngredientView();
            return;
        }

        updateStepView();
        loadVideo();

        Step step = recipe.getSteps().get(stepPos);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(step.getShortDescription());

        if (stepPos >= recipe.getSteps().size() - 1) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
        }


        if(! isPlayerFullScreen) {
            showDetailsForStep(step);
        }
    }

    private void showDetailsForStep(Step step) {
        detailsTextView.setText(step.getDescription());

        String image = step.getThumbnailURL();
        if (!image.isEmpty())
            Picasso.with(getContext()).load(image).error(R.drawable.no_image).into(thumbNailImageView);
        else
            thumbNailImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.no_image));
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
        playerView.setVisibility(GONE);
        noVideoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    public void updateContent(int pos) {
        getArguments().putInt(EXTRA_POSITION, pos);
        position = pos;
        exoPlayer.stop();
        updateContent();
    }


    @Override
    public void ingredientChecked(int position, boolean checked) {
        recipe.getIngredients().get(position).setChecked(checked);
    }


}