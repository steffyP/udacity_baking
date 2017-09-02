package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nanodegree.udacity.stefanie.at.bakingmaster.R;

/**
 * Created by steffy on 02/09/2017.
 */

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);


        return view;

    }


}
