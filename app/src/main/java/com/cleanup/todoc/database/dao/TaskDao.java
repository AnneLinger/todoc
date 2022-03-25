package com.cleanup.todoc.database.dao;


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

    //Recover all the tasks
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    //Recover a task from the db with its id
    @Query("SELECT * FROM Task WHERE task_id = :taskId")
    LiveData<Task> getTask(long taskId);

    //Add a new task to the db
    @Insert
    long createTask(Task task);

    //Update a task in the db
    @Update
    void updateTask(Task task);

    //Delete a task from the db
    @Query("DELETE FROM Task WHERE task_id = :taskId")
    void deleteTask(long taskId);

    //Sort tasks by alphabetical order
    @Query("SELECT t.* FROM Task AS t JOIN project AS p on p.id = t.project_id ORDER BY p.name")
    LiveData<List<Task>> sortTasksByAlphabeticalOrder();

    //Sort tasks by reverse alphabetical order
    @Query("SELECT t.* FROM Task AS t JOIN project AS p on p.id = t.project_id ORDER BY p.name DESC")
    LiveData<List<Task>> sortTasksByReverseAlphabeticalOrder();

    //Sort tasks by creation timestamp order
    @Query("SELECT * FROM Task ORDER BY task_creation_timestamp")
    LiveData<List<Task>> sortTasksByCreationTimestampOrder();

    //Sort tasks by reverse creation timestamp order
    @Query("SELECT * FROM Task ORDER BY task_creation_timestamp DESC")
    LiveData<List<Task>> sortTasksByReverseCreationTimestampOrder();
}
