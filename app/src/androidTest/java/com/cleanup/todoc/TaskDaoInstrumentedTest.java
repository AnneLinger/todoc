package com.cleanup.todoc;

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
*Instrumented tests for TaskDao interface
*/
public class TaskDaoInstrumentedTest {
    //For Data
    private TodocDatabase mDatabase;

    //Data set for tests
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "TestProject", 0xFFEADAD1);
    private static Task TASK_DEMO = new Task(PROJECT_ID, "TestTask", 8);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    //Test insert and get a project
    @Test
    public void insertAndGetProject() throws InterruptedException {
        //Add PROJECT_DEMO to the db with createProject()
        this.mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Recover the PROJECT_DEMO in the db with getProject()
        Project project = LiveDataTestUtil.getValue(this.mDatabase.mProjectDao().getProject(PROJECT_ID));

        //Assert that the project which has been recovered equals to PROJECT_DEMO
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    //Test get tasks when db has no task
    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    //Test insert and get a task
    @Test
    public void insertAndGetTask() throws InterruptedException {
        //Add a project in the db
        this.mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        this.mDatabase.mTaskDao().insertTask(TASK_DEMO);

        //Assert that db contains one task
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.size() == 1);
    }

    //Test insert and update a task
    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        //Add a project to the db
        this.mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        this.mDatabase.mTaskDao().insertTask(TASK_DEMO);

        //Recover the task and update it
        Task taskToUpdate= LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTask(TASK_DEMO.getId()));
        taskToUpdate.setName("UpdatedTestTask");
        this.mDatabase.mTaskDao().updateTask(taskToUpdate);

        //Assert that db contains one task whose name is updated
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.size()==1 && tasks.get(0).getName().equals("UpdatedTestTask"));
    }

    //Test insert and delete a task
    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        //Add a project to the db
        this.mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        this.mDatabase.mTaskDao().insertTask(TASK_DEMO);

        //Recover the task to delete it
        Task taskToDelete = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTask(TASK_DEMO.getId()));
        this.mDatabase.mTaskDao().deleteTask(taskToDelete.getId());

        //Assert that tasks are empty now
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
