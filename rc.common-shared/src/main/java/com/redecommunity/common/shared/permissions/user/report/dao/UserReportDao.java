package com.redecommunity.common.shared.permissions.user.report.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.report.data.ReportReason;
import com.redecommunity.common.shared.report.manager.ReportReasonManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class UserReportDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_report_users";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`user_id` INTEGER NOT NULL," +
                                "`report_reason_id` INTEGER NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    public <U extends User, R extends ReportReason> void insert(U user, R reportReason) {
        String query = String.format(
                "INSERT INTO %s " +
                        "(" +
                        "`user_id`," +
                        "`report_reason_id`" +
                        ")" +
                        " VALUES " +
                        "(" +
                        "%d," +
                        "%d" +
                        ");",
                this.getTableName(),
                user.getId(),
                reportReason.getId()
        );

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public <K, V, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d;",
                this.getTableName(),
                key,
                value
        );

        Set<T> reports = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Integer reportReasonId = resultSet.getInt("report_reason_id");

                ReportReason reportReason = ReportReasonManager.getReportReason(reportReasonId);

                reports.add((T) reportReason);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return reports;
    }
}
