package com.cleanup.todoc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.utils.TestUtils.withRecyclerView;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    //For data
    private TodocDatabase mDatabase;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(1));

        onView(withId(R.id.img_delete)).perform(click());

        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void sortTasks() {
        MainActivity activity = rule.getActivity();

        //Add tasks to the db
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example 1"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(containsString("Projet Lucidia"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example 2"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(containsString("Projet Tartampion"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example 3"));
        onView(withId(R.id.project_spinner)).perform(click());
        onView(withText(containsString("Projet Circus"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        //Assert that tasks are displayed in the recycler view
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 1")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 2")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 3")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_project_name))
                .check(matches(withText("Projet Circus")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_project_name))
                .check(matches(withText("Projet Lucidia")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_project_name))
                .check(matches(withText("Projet Tartampion")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_project_name))
                .check(matches(withText("Projet Tartampion")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_project_name))
                .check(matches(withText("Projet Lucidia")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_project_name))
                .check(matches(withText("Projet Circus")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 1")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 2")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 3")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 3")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 2")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Tâche example 1")));
    }
}
