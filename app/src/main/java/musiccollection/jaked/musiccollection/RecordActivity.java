package musiccollection.jaked.musiccollection;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecordActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return RecordFragment.newInstance();
    }
}