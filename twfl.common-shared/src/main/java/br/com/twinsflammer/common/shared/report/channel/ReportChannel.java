package br.com.twinsflammer.common.shared.report.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.report.manager.ReportReasonManager;

/**
 * Created by @SrGutyerrez
 */
public class ReportChannel extends Channel {
    @Override
    public String getName() {
        return ReportReasonManager.CHANNEL_NAME;
    }
}
