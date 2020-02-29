package com.redecommunity.common.shared.report.listener;

import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redecommunity.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.report.data.ReportReason;
import com.redecommunity.common.shared.report.manager.ReportReasonManager;
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
