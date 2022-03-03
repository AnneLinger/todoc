package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

/**
 * Dao interface to manage CRUD actions on Project table
 */
@Dao
public interface ProjectDao {

    //Create a project in the db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    //Recover the project of a task
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);
}
