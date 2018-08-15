package musiccollection.jaked.musiccollection;


import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CustomAlbumEspressoTest {

    private static final String EDIT_ALBUM = "EditAlbum";

    @Rule
    public ActivityTestRule<CustomAlbumActivity> mCustomAlbumActivityActivityTestRule = new
            ActivityTestRule<CustomAlbumActivity>(CustomAlbumActivity.class);

    @Test
    public void checkEditTextViewsExists(){
        onView(withId(R.id.etArtistName)).check(matches(isDisplayed()));
        onView(withId(R.id.etArtistName)).perform(typeText("Bob Dylan"));
        onView(withId(R.id.etArtistName)).check(matches(withText("Bob Dylan")));
    }

    @Test
    public void checkViewsMatchCustomAlbumIntent(){
        Album album = new Album("Here Are the Sonics","The Sonics","1965",true);
        album.setRating(5);
        Intent intent = new Intent();
        intent.putExtra(EDIT_ALBUM, album);
        mCustomAlbumActivityActivityTestRule.launchActivity(intent);

        onView(withId(R.id.etArtistName)).check(matches(withText(album.getArtistName())));
        onView(withId(R.id.etAlbumName)).check(matches(withText(album.getAlbumTitle())));
        onView(withId(R.id.etReleaseYear)).check(matches(withText(album.getReleaseYear())));
        onView(withId(R.id.cbOfficial)).check(matches(isChecked()));
        onView(withId(R.id.rbRating)).check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsWithoutCustomAlbum(){
        Album album = null;
        Intent intent = new Intent();
        intent.putExtra(EDIT_ALBUM, album);
        mCustomAlbumActivityActivityTestRule.launchActivity(intent);

        onView(withId(R.id.etArtistName)).check(matches(withText("")));
        onView(withId(R.id.etAlbumName)).check(matches(withText("")));
        onView(withId(R.id.etReleaseYear)).check(matches(withText("")));
        onView(withId(R.id.cbOfficial)).check(matches(withText("")));
        onView(withId(R.id.rbRating)).check(matches(isDisplayed()));
    }





}
