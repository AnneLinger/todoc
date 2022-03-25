package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.util.TestExecutor;
import com.cleanup.todoc.view.TaskViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Unit tests for the TaskViewModel
 */

public class TaskViewModelUnitTest {

    //For repositories
    private final ProjectDataRepository mProjectDataRepository = mock(ProjectDataRepository.class);
    private final TaskDataRepository mTaskDataRepository = mock(TaskDataRepository.class);

    //For threads
    private final Executor mExecutor = spy(new TestExecutor());

    //For ViewModel
    private TaskViewModel mTaskViewModel;

    //Data set for tests
    private final Project PROJECT_DEMO = new Project(1, "TestProject", 0xFFEADAD1);
    private final Task TASK_DEMO = new Task(1, "TestTask", 8);

    @Before
    public void setUp() {
        mTaskViewModel = new TaskViewModel(mTaskDataRepository, mProjectDataRepository, mExecutor);
    }

    //Test get all projects
    @Test
    public void getProjects() {
        //Recover the projects with the repository
        LiveData<List<Project>> mExpectedProjects = new MutableLiveData<>();
        when(mProjectDataRepository.getProjects()).thenReturn(mExpectedProjects);

        //Recover the projects with the ViewModel
        LiveData<List<Project>> mProjects = mTaskViewModel.getProjects();

        //Assert that the two lists are the same
        assertEquals(mExpectedProjects, mProjects);
        verify(mProjectDataRepository, atLeastOnce()).getProjects();
        verifyNoMoreInteractions(mProjectDataRepository);
    }

    //Test get one project
    @Test
    public void getProject() {
        //Recover PROJECT_DEMO with the repository
        LiveData<Project> mExpectedProject = new MutableLiveData<>();
        when(mProjectDataRepository.getProject(PROJECT_DEMO.getId())).thenReturn(mExpectedProject);

        //Recover PROJECT_DEMO with the ViewModel
        LiveData<Project> mProject = mTaskViewModel.getProject(PROJECT_DEMO.getId());

        //Assert that the two projects are the same
        assertEquals(mExpectedProject, mProject);
        verify(mProjectDataRepository, atLeastOnce()).getProject(PROJECT_DEMO.getId());
        verifyNoMoreInteractions(mProjectDataRepository);
    }

    //Test create a new project
    @Test
    public void createProject() {
        //Create a new project with the ViewModel
        mTaskViewModel.createProject(PROJECT_DEMO);

        //Verify that PROJECT_DEMO has also been created by the repository
        verify(mProjectDataRepository).createProject(PROJECT_DEMO);
        verifyNoMoreInteractions(mProjectDataRepository);
    }

    //Test delete a project
    @Test
    public void deleteProject() {
        //Delete a project with the ViewModel
        mTaskViewModel.deleteProject(PROJECT_DEMO.getId());

        //Verify that PROJECT_DEMO has also been deleted by the repository
        verify(mProjectDataRepository).deleteProject(PROJECT_DEMO.getId());
        verifyNoMoreInteractions(mProjectDataRepository);
    }

    //Test get all tasks
    @Test
    public void getTasks() {
        //Recover the tasks with repository
        LiveData<List<Task>> mExpectedTasks = new MutableLiveData<>();
        when(mTaskDataRepository.getTasks()).thenReturn(mExpectedTasks);

        //Recover the tasks with the ViewModel
        LiveData<List<Task>> mTasks = mTaskViewModel.getTasks();

        //Assert that the two lists are the same
        assertEquals(mExpectedTasks, mTasks);
        verify(mTaskDataRepository, atLeastOnce()).getTasks();
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test get one task
    @Test
    public void getTask() {
        //Recover TASK_DEMO with the repository
        LiveData<Task> mExpectedTask = new MutableLiveData<>();
        when(mTaskDataRepository.getTask(TASK_DEMO.getId())).thenReturn(mExpectedTask);

        //Recover TASK_DEMO with the ViewModel
        LiveData<Task> mTask = mTaskViewModel.getTask(TASK_DEMO.getId());

        //Assert that the two tasks are the same
        assertEquals(mExpectedTask, mTask);
        verify(mTaskDataRepository, atLeastOnce()).getTask(TASK_DEMO.getId());
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test create a new task
    @Test
    public void createTask() {
        //Create a new task with the ViewModel
        mTaskViewModel.createTask(TASK_DEMO);

        //Verify that TASK_DEMO has also been created by the repository
        verify(mTaskDataRepository).createTask(TASK_DEMO);
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test update a task
    @Test
    public void updateTask() {
        //Update a task with the ViewModel
        mTaskViewModel.updateTask(TASK_DEMO);

        //Verify that TASK_DEMO has also been updated by the repository
        verify(mTaskDataRepository).updateTask(TASK_DEMO);
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test delete a task
    @Test
    public void deleteTask() {
        //Delete a task with the ViewModel
        mTaskViewModel.deleteTask(TASK_DEMO.getId());

        //Verify that TASK_DEMO has also been deleted by the repository
        verify(mTaskDataRepository).deleteTask(TASK_DEMO.getId());
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test sort tasks by alphabetical order
    @Test
    public void sortTasksByAlphabeticalOrder() {
        //Sort tasks with the ViewModel
        mTaskViewModel.sortTasksByAlphabeticalOrder();

        //Verify that tasks have also been sort by the repository
        verify(mTaskDataRepository).sortTasksByAlphabeticalOrder();
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test sort tasks by reverse alphabetical order
    @Test
    public void sortTasksByReverseAlphabeticalOrder() {
        //Sort tasks with the ViewModel
        mTaskViewModel.sortTasksByReverseAlphabeticalOrder();

        //Verify that tasks have also been sort by the repository
        verify(mTaskDataRepository).sortTasksByReverseAlphabeticalOrder();
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test sort tasks by creation timestamp order
    @Test
    public void sortTasksByCreationTimestampOrder() {
        //Sort tasks with the ViewModel
        mTaskViewModel.sortTasksByCreationTimestampOrder();

        //Verify that tasks have also been sort by the repository
        verify(mTaskDataRepository).sortTasksByCreationTimestampOrder();
        verifyNoMoreInteractions(mTaskDataRepository);
    }

    //Test sort tasks by reverse creation timestamp order
    @Test
    public void sortTasksByReverseCreationTimestampOrder() {
        //Sort tasks with the ViewModel
        mTaskViewModel.sortTasksByReverseCreationTimestampOrder();

        //Verify that tasks have also been sort by the repository
        verify(mTaskDataRepository).sortTasksByReverseCreationTimestampOrder();
        verifyNoMoreInteractions(mTaskDataRepository);
    }
}
