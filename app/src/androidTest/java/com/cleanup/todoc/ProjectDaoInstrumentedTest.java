package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
*Instrumented tests for ProjectDao interface
*/

public class ProjectDaoInstrumentedTest {

    //For Data
    private TodocDatabase mDatabase;

    //Data set for tests
    private static final long PROJECT_ID = 1;
    private static final long SECOND_PROJECT_ID = 2;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID,"TestProject", 0xFFEADAD1);
    private static final Project SECOND_PROJECT_DEMO = new Project(SECOND_PROJECT_ID, "SecondTestProject", 0xFFEADAD1);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    //Test get all projects from the db
    @Test
    public void getAllProjects() throws InterruptedException {
        //Add the two test projects to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);
        mDatabase.mProjectDao().createProject(SECOND_PROJECT_DEMO);

        //Recover the two projects from the db
        List<Project> projects = LiveDataTestUtil.getValue(mDatabase.mProjectDao().getProjects());

        //Assert that the list contains the two test projects
        assertEquals(projects.get(0).getId(), PROJECT_DEMO.getId());
        assertEquals(projects.get(1).getId(), SECOND_PROJECT_DEMO.getId());
    }

    //Test get a project from the db
    @Test
    public void getProject() throws InterruptedException {
        //Add the PROJECT_DEMO to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Recover the PROJECT_DEMO from the db
        Project project = LiveDataTestUtil.getValue(mDatabase.mProjectDao().getProject(PROJECT_ID));

        //Assert that the project which has been recovered equals to PROJECT_DEMO
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    //Test create a project in the db
    @Test
    public void createProject() throws InterruptedException {
        //Add the PROJECT-DEMO to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Assert that db contains PROJECT_DEMO
        Project project = LiveDataTestUtil.getValue(mDatabase.mProjectDao().getProject(PROJECT_ID));
        assertEquals(project.getId(), PROJECT_DEMO.getId());
    }

    //Test delete a project from the db
    @Test
    public void deleteProject() throws InterruptedException {
        //Add the PROJECT_DEMO to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Delete the project from the db
        mDatabase.mProjectDao().deleteProject(PROJECT_DEMO.getId());

        //Assert that the project is not yet in the db
        List<Project> projects = LiveDataTestUtil.getValue(mDatabase.mProjectDao().getProjects());
        assertTrue(projects.isEmpty());
    }
}
