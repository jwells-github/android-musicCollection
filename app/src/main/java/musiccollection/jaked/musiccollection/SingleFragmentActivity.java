package musiccollection.jaked.musiccollection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by   jaked on 26/02/2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        // Gets the frameLayout, where the fragments will be hosted
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // If a fragment wasn't already loaded
        // A fragment may already be loaded if the view is rotated
        if (fragment == null ){

            fragment = createFragment();
            //adds the ExerciseFragment (Where exercise details are entered) to the frameLayout
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
