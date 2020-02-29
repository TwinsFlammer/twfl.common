package com.redecommunity.common.shared.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by @SrGutyerrez
 */
public class SchedulerManager {
    private ScheduledExecutorService scheduledExecutorService;

    public SchedulerManager() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }
}
