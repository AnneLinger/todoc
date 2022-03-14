package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
*Repository for projects
*/

public class ProjectDataRepository {

    //For data
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    //Get the list of the projects
    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getProjects();
    }

    //Get a project
    public LiveData<Project> getProject(long projectId) {
        return mProjectDao.getProject(projectId);
    }

    //Create a project
    public void createProject(Project project) {
        mProjectDao.createProject(project);
    }

    //Delete a task from the db
    public void deleteProject(long projectId) {
        mProjectDao.deleteProject(projectId);
    }
}
