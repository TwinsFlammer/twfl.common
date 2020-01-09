package com.redecommunity.common.shared.report.manager;

import com.google.common.collect.Lists;
import com.redecommunity.common.shared.report.dao.ReportReasonDao;
import com.redecommunity.common.shared.report.data.ReportReason;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class ReportReasonManager {
    public static final String CHANNEL_NAME = "report_channel";

    private static List<ReportReason> reportReasons = Lists.newArrayList();

    public ReportReasonManager() {
        ReportReasonDao reportReasonDao = new ReportReasonDao();

        ReportReasonManager.reportReasons.addAll(reportReasonDao.findAll());
    }

    public static ReportReason getReportReason(Integer id) {
        return ReportReasonManager.reportReasons
                .stream()
                .filter(reportReason -> reportReason.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static ReportReason getReportReason(String name) {
        return ReportReasonManager.reportReasons
                .stream()
                .filter(reportReason -> reportReason.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static ReportReason toReportReason(ResultSet resultSet) throws SQLException {
        return new ReportReason(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("display_name"),
                resultSet.getString("description")
        );
    }
}
