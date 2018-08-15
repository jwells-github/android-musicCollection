package musiccollection.jaked.musiccollection;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AlbumPickerFragmentEspressoTest {

    @Rule
    public ActivityTestRule<RecordListActivity> mRecordListActivityActivityTestRule = new
            ActivityTestRule<RecordListActivity>(RecordListActivity.class);


    @Test
    public void checkSearchBar(){
        onView(withId(R.id.menu_item_search)).perform(click());
        onView(withId(R.id.menu_item_search)).check(matches(isDisplayed()));
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(typeText("test"));
        onView(isAssignableFrom(AutoCompleteTextView.class)).check(matches(withText("test")));
    }

    @Test
    public void checkAlbumPickerFragmentDisplayed(){
        onView(withId(R.id.menu_item_search)).perform(click());
        onView(isAssignableFrom(AutoCompleteTextView.class))
                .perform(typeText("The White Stripes"));
        onView(isAssignableFrom(AutoCompleteTextView.class)).perform(pressImeActionButton());
        onView(withId(R.id.lvAlbumPicker)).check(matches(isDisplayed()));
    }

}


