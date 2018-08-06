package musiccollection.jaked.musiccollection;

import android.support.v4.app.Fragment;

public class CustomAlbumActivity extends SingleFragmentActivity{



    @Override
    protected Fragment createFragment() {
        return CustomAlbumFragment.newInstance();
    }
}

