package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

/**
*Repository for projects
*/
public class ProjectDataRepository {
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    //Create a project
    public void createProject(Project project) {
        mProjectDao.createProject(project);
    }

    //Get a project
    public LiveData<Project> getProject(long projectId) {
        return mProjectDao.getProject(projectId);
    }
}
