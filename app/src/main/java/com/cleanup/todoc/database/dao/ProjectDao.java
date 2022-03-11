package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Dao interface to manage CRUD actions on Project table
 */
@Dao
public interface ProjectDao {

    //Recover all the projects
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    //Recover the project of a task
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    //Add a new project to the db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    //Update a project in the db
    @Update
    void updateProject(Project project);

    //Delete a project from the db
    @Query("DELETE FROM Project WHERE id = :projectId")
    void deleteProject(long projectId);
}
