package com.cleanup.todoc.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
*ViewModel to recover data for MainActivity
*/
public class TaskViewModel extends ViewModel {
    //Repositories
    private final TaskDataRepository mTaskDataRepository;
    private final ProjectDataRepository mProjectDataRepository;
    private final Executor mExecutor;

    //Constructor
    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
        mTaskDataRepository = taskDataRepository;
        mProjectDataRepository = projectDataRepository;
        mExecutor = executor;
    }

    //For projects

    public LiveData<List<Project>> getProjects() {
        return mProjectDataRepository.getProjects();
    }

    public void createProject(Project project) {
        mProjectDataRepository.createProject(project);
    }

    public LiveData<Project> getProject(long projectId) {
        return mProjectDataRepository.getProject(projectId);
    }

    //For tasks
    public LiveData<List<Task>> getTasks() {
        return mTaskDataRepository.getTasks();
    }

    public LiveData<Task> getTask(long taskId) {
        return mTaskDataRepository.getTask(taskId);
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.createTask(task);
        });
    }

    public void updateTask(Task task) {
        mExecutor.execute(() -> {
            mTaskDataRepository.updateTask(task);
        });
    }

    public void deleteTask(long taskId) {
        mExecutor.execute(() -> {
            mTaskDataRepository.deleteTask(taskId);
        });
    }
}
