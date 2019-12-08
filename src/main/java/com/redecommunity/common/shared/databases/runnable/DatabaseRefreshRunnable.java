package com.redecommunity.common.shared.databases.runnable;

import com.redecommunity.common.Common;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseRefreshRunnable implements Runnable {
    @Override
    public void run() {
        Common.getInstance().getDatabaseManager().refresh();
    }
}
