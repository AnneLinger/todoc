package com.cleanup.todoc.util;

import java.util.concurrent.Executor;

/**
*Util class to use Executor class in unit tests
*/

public class TestExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
