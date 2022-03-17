package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectDataRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit tests for the ProjectDataRepository
 */

public class ProjectRepositoryUnitTest {

    //For Database
    private final ProjectDao mProjectDao = mock(ProjectDao.class);

    //For repository
    private ProjectDataRepository mProjectDataRepository;

    //Data set for tests
    private final Project PROJECT_DEMO = new Project(1, "TestProject", 0xFFEADAD1);

    @Before
    public void setUp() {
        mProjectDataRepository = new ProjectDataRepository(mProjectDao);
    }

    //Test get all projects
    @Test
    public void getProjects() {
        //Recover the projects with Dao
        LiveData<List<Project>> mExpectedProjects = new MutableLiveData<>();
        when(mProjectDao.getProjects()).thenReturn(mExpectedProjects);

        //Recover the projects with the repository
        LiveData<List<Project>> mProjects = mProjectDataRepository.getProjects();

        //Assert that the two lists are the same
        assertEquals(mExpectedProjects, mProjects);
        verify(mProjectDao, atLeastOnce()).getProjects();
        verifyNoMoreInteractions(mProjectDao);
    }

    //Test get one project
    @Test
    public void getProject() {
        //Recover PROJECT_DEMO with the dao
        LiveData<Project> mExpectedProject = new MutableLiveData<>();
        when(mProjectDao.getProject(PROJECT_DEMO.getId())).thenReturn(mExpectedProject);

        //Recover PROJECT_DEMO with the repository
        LiveData<Project> mProject = mProjectDataRepository.getProject(PROJECT_DEMO.getId());

        //Assert that the two projects are the same
        assertEquals(mExpectedProject, mProject);
        verify(mProjectDao, atLeastOnce()).getProject(PROJECT_DEMO.getId());
        verifyNoMoreInteractions(mProjectDao);
    }

    //Test create a new project
    @Test
    public void createProject() {
        //Create a new project with the repository
        mProjectDataRepository.createProject(PROJECT_DEMO);

        //Verify that PROJECT_DEMO has also been created by the Dao
        verify(mProjectDao).createProject(PROJECT_DEMO);
        verifyNoMoreInteractions(mProjectDao);
    }

    //Test delete a project
    @Test
    public void deleteProject() {
        //Delete a project with the repository
        mProjectDataRepository.deleteProject(PROJECT_DEMO.getId());

        //Verify that PROJECT_DEMO has also been deleted by the Dao
        verify(mProjectDao).deleteProject(PROJECT_DEMO.getId());
        verifyNoMoreInteractions(mProjectDao);
    }
}
