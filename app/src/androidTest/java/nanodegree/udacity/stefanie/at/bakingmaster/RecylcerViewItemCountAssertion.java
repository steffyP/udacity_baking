package nanodegree.udacity.stefanie.at.bakingmaster;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by steffy on 03/09/2017.
 * <p>
 * <p>
 * Contribution to https://stackoverflow.com/questions/36399787/how-to-count-recyclerview-items-with-espresso#
 */

public class RecylcerViewItemCountAssertion implements ViewAssertion {

    private final int expectedCount;

    public RecylcerViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(expectedCount));
    }


}
