package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskDataRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for the TaskDataRepository
 */

public class TaskRepositoryUnitTest {

    //For Database
    private final TaskDao mTaskDao = mock(TaskDao.class);

    //For repository
    private TaskDataRepository mTaskDataRepository;

    //Data set for tests
    private final Task TASK_DEMO = new Task(1, "TestTask", 8);

    @Before
    public void setUp() {
        mTaskDataRepository = new TaskDataRepository(mTaskDao);
    }

    //Test get all tasks
    @Test
    public void getTasks() {
        //Recover the tasks with Dao
        LiveData<List<Task>> mExpectedTasks = new MutableLiveData<>();
        when(mTaskDao.getTasks()).thenReturn(mExpectedTasks);

        //Recover the tasks with the repository
        LiveData<List<Task>> mTasks = mTaskDataRepository.getTasks();

        //Assert that the two lists are the same
        assertEquals(mExpectedTasks, mTasks);
        verify(mTaskDao, atLeastOnce()).getTasks();
        verifyNoMoreInteractions(mTaskDao);
    }

    //Test get one task
    @Test
    public void getTask() {
        //Recover TASK_DEMO with the Dao
        LiveData<Task> mExpectedTask = new MutableLiveData<>();
        when(mTaskDao.getTask(TASK_DEMO.getId())).thenReturn(mExpectedTask);

        //Recover TASK_DEMO with the repository
        LiveData<Task> mTask = mTaskDataRepository.getTask(TASK_DEMO.getId());

        //Assert that the two tasks are the same
        assertEquals(mExpectedTask, mTask);
        verify(mTaskDao, atLeastOnce()).getTask(TASK_DEMO.getId());
        verifyNoMoreInteractions(mTaskDao);
    }

    //Test create a new task
    @Test
    public void createTask() {
        //Create a new task with the repository
        mTaskDataRepository.createTask(TASK_DEMO);

        //Verify that TASK_DEMO has also been created by the Dao
        verify(mTaskDao).createTask(TASK_DEMO);
        verifyNoMoreInteractions(mTaskDao);
    }

    //Test update a task
    @Test
    public void updateTask() {
        //Update a task with the repository
        mTaskDataRepository.updateTask(TASK_DEMO);

        //Verify that TASK_DEMO has also been updated by the Dao
        verify(mTaskDao).updateTask(TASK_DEMO);
        verifyNoMoreInteractions(mTaskDao);
    }

    //Test delete a task
    @Test
    public void deleteTask() {
        //Delete a task with the repository
        mTaskDataRepository.deleteTask(TASK_DEMO.getId());

        //Verify that TASK_DEMO has also been deleted by the Dao
        verify(mTaskDao).deleteTask(TASK_DEMO.getId());
        verifyNoMoreInteractions(mTaskDao);
    }
}
