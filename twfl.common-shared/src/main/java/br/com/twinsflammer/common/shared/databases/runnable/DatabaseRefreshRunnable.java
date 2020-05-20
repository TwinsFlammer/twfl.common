package br.com.twinsflammer.common.shared.databases.runnable;

import br.com.twinsflammer.common.shared.Common;

/**
 * Created by @SrGutyerrez
 */
public class DatabaseRefreshRunnable implements Runnable {
    @Override
    public void run() {
        Common.getInstance().getDatabaseManager().refresh();
    }
}
