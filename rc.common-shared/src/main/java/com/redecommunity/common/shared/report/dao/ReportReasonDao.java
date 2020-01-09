package com.redecommunity.common.shared.report.dao;

import com.google.common.collect.Sets;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.proxy.report.data.ReportReason;
import com.redecommunity.proxy.report.manager.ReportReasonManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class ReportReasonDao extends Table {
    @Override
    public String getDatabaseName() {
        return "general";
    }

    @Override
    public String getTableName() {
        return "server_report_reasons";
    }

    @Override
    public void createTable() {
        this.execute(
                String.format(
                        "CREATE TABLE IF NOT EXISTS %s " +
                                "(" +
                                "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                                "`name` VARCHAR(255) NOT NULL," +
                                "`display_name` VARCHAR(255) NOT NULL," +
                                "`description` VARCHAR(255) NOT NULL" +
                                ");",
                        this.getTableName()
                )
        );
    }

    @Override
    public <T> Set<T> findAll() {
        String query = String.format(
                "SELECT * FROM %s;",
                this.getTableName()
        );

        Set<T> reports = Sets.newConcurrentHashSet();

        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                ReportReason reportReason = ReportReasonManager.toReportReason(resultSet);

                reports.add((T) reportReason);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return reports;
    }

    public <K, V, T> Set<T> findAll(K key, V value) {
        String query = String.format(
                "SELECT * FROM %s WHERE `%s`=%d",
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
                ReportReason reportReason = ReportReasonManager.toReportReason(resultSet);

                reports.add((T) reportReason);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return reports;
    }
}
