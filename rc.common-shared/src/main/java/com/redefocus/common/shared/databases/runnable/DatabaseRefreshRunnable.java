package com.redefocus.common.shared.databases.runnable;

import com.redefocus.common.shared.Common;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseRefreshRunnable implements Runnable {
    @Override
    public void run() {
        Common.getInstance().getDatabaseManager().refresh();
    }
}
