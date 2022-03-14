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
*Instrumented tests for TaskDao interface
*/

public class TaskDaoInstrumentedTest {

    //For Data
    private TodocDatabase mDatabase;

    //Data set for tests
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID,"TestProject", 0xFFEADAD1);
    private static Task TASK_DEMO = new Task(PROJECT_ID, "TestTask", 8);
    private static Task SECOND_TASK_DEMO = new Task(PROJECT_ID, "SecondTestTask", 9);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class).allowMainThreadQueries().build();
        TASK_DEMO.setId(1);
        SECOND_TASK_DEMO.setId(2);
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    //Test get all tasks from the db
    @Test
    public void getAllTasks() throws InterruptedException {
        //Add a project in the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add the two test tasks to the project
        mDatabase.mTaskDao().CreateTask(TASK_DEMO);
        mDatabase.mTaskDao().CreateTask(SECOND_TASK_DEMO);

        //Recover the two tasks from the db
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTasks());

        //Assert that the list contains the two test projects
        assertEquals(2, tasks.size());
    }

    //Test get a task from the db
    @Test
    public void getTask() throws InterruptedException {
        //Add a project in the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        mDatabase.mTaskDao().CreateTask(TASK_DEMO);

        //Recover the TASK_DEMO from the db
        Task task = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTask(TASK_DEMO.getId()));

        //Assert that the project which has been recovered equals to PROJECT_DEMO
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_DEMO.getId());
    }

    //Test insert and update a task
    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        //Add a project to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        mDatabase.mTaskDao().CreateTask(TASK_DEMO);

        //Recover the task and update it
        Task taskToUpdate= LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTask(TASK_DEMO.getId()));
        taskToUpdate.setName("UpdatedTestTask");
        mDatabase.mTaskDao().updateTask(taskToUpdate);

        //Assert that db contains one task whose name is updated
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.size()==1 && tasks.get(0).getName().equals("UpdatedTestTask"));
    }

    //Test insert and delete a task
    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        //Add a project to the db
        mDatabase.mProjectDao().createProject(PROJECT_DEMO);

        //Add a task to the project
        mDatabase.mTaskDao().CreateTask(TASK_DEMO);

        //Recover the task to delete it
        Task taskToDelete = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTask(TASK_DEMO.getId()));
        mDatabase.mTaskDao().deleteTask(taskToDelete.getId());

        //Assert that tasks are empty now in the db
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.mTaskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
