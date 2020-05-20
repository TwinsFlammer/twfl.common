package br.com.twinsflammer.common.shared.report.manager;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.report.dao.ReportReasonDao;
import br.com.twinsflammer.common.shared.report.data.ReportReason;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class ReportReasonManager {
    public static final String CHANNEL_NAME = "report_channel";

    private static List<ReportReason> reportReasons = Lists.newArrayList();

    public ReportReasonManager() {
        ReportReasonDao reportReasonDao = new ReportReasonDao();

        Common.getInstance().getScheduler().scheduleWithFixedDelay(
                () -> {
                    Set<ReportReason> reports = reportReasonDao.findAll();

                    ReportReasonManager.reportReasons = Lists.newArrayList(reports);
                },
                0,
                1,
                TimeUnit.MINUTES
        );
    }

    public static List<ReportReason> getReportReasons() {
        return ReportReasonManager.reportReasons;
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
                .filter(reportReason -> reportReason.getName().equalsIgnoreCase(name))
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
