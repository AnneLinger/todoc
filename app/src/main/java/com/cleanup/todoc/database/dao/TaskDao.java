package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Dao interface to manage CRUD actions on Task table
 */
@Dao
public interface TaskDao {

    //Recover all the tasks of a project with LiveData type
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasks(long projectId);

    //Recover all the tasks of a project with Cursor type
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    Cursor getTasksWithCursor(long projectId);

    //Recover a task from the db
    @Query("SELECT * FROM Task WHERE id = :taskId")
    LiveData<Task> getTask(long taskId);

    //Add a new task to the db
    @Insert
    long insertTask(Task task);

    //Update a task in the db
    @Update
    int updateTask(Task task);

    //Delete a task from the db
    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);

}
