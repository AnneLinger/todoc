package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
*Repository for Tasks
*/
public class TaskDataRepository {
    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    //Get the list of the tasks
    public LiveData<List<Task>> getTasks(long projectId) {
        return mTaskDao.getTasks(projectId);
    }

    //Get a task
    public LiveData<Task> getTask(long taskId) {
        return mTaskDao.getTask(taskId);
    }

    //Create a task
    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    //Update a task
    public void updateTask(Task task) {
        mTaskDao.updateTask(task);
    }

    //Delete a task
    public void deleteTask(long taskId) {
        mTaskDao.deleteTask(taskId);
    }
}
