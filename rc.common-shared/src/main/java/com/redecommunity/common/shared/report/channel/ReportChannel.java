package com.redecommunity.common.shared.report.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.report.manager.ReportReasonManager;

/**
 * Created by @SrGutyerrez
 */
public class ReportChannel extends Channel {
    @Override
    public String getName() {
        return ReportReasonManager.CHANNEL_NAME;
    }
}
