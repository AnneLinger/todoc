package com.cleanup.todoc.injections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.view.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
*ViewModel constructor
*/
public class ViewModelFactory implements ViewModelProvider.Factory {

    //For data
    private final TaskDataRepository mTaskDataRepository;
    private final ProjectDataRepository mProjectDataRepository;

    //For threads
    private final Executor mExecutor;

    //For constructor
    private static ViewModelFactory sFactory;

    //Instance
    public static ViewModelFactory getInstance(Context context) {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(context);
                }
            }
        }
        return sFactory;
    }

    //Factory
    private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        mTaskDataRepository = new TaskDataRepository(database.mTaskDao());
        mProjectDataRepository = new ProjectDataRepository(database.mProjectDao());
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mTaskDataRepository, mProjectDataRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
