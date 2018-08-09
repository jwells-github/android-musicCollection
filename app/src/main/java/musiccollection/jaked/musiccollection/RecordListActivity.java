package musiccollection.jaked.musiccollection;

import android.support.v4.app.Fragment;

public class RecordListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return RecordListFragment.newInstance();
    }
}