package iamdilipkumar.com.locationtracking.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import iamdilipkumar.com.locationtracking.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ScheduleActivityTest {

    @Rule
    public ActivityTestRule<ScheduleActivity> mActivityTestRule = new ActivityTestRule<>(ScheduleActivity.class);

    @Test
    public void scheduleActivityTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.btn_cancel), withText("Cancel"), isDisplayed()));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btn_start_stop_shift), withText("Start shift"), isDisplayed()));
        button2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.btn_yes), withText("OK"), isDisplayed()));
        button3.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.btn_start_stop_shift), withText("Start shift"), isDisplayed()));
        button4.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.btn_start_stop_shift), withText("Stop shift"), isDisplayed()));
        button5.perform(click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.btn_yes), withText("OK"), isDisplayed()));
        button6.perform(click());

    }

}
