package com.redecommunity.common.shared.databases.mysql.dao;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
interface TableImpl {

    void createTable() throws SQLException;

    String getTableName() throws SQLException;

    <T> void insert(T object) throws SQLException;

    <K, V> void update(K key, V value) throws SQLException;

    <K, V> void delete(K key, V value) throws SQLException;

    <K, V, T> T findOne(K key, V value) throws SQLException;

    <T> Set<T> findAll() throws SQLException;

}