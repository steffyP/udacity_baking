package nanodegree.udacity.stefanie.at.bakingmaster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import nanodegree.udacity.stefanie.at.bakingmaster.fragment.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_RECIPE_LIST = "fragment_recipe_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getString(R.string.app_name));

    }


}
