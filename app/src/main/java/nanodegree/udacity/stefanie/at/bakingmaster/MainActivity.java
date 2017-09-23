package nanodegree.udacity.stefanie.at.bakingmaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        // the "magic" happens in the layout, the fragment is included
    }


}
