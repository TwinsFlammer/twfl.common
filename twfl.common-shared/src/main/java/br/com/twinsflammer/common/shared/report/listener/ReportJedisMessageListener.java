package br.com.twinsflammer.common.shared.report.listener;

import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.report.data.ReportReason;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.report.manager.ReportReasonManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class ReportJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = ReportReasonManager.CHANNEL_NAME)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer reportReasonId = ((Long) jsonObject.get("report_reason_id")).intValue();

        User user = UserManager.getUser(userId);

        ReportReason reportReason = ReportReasonManager.getReportReason(reportReasonId);

        user.addReport(reportReason);
    }
}
