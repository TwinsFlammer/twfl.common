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

    <K, K1, V, V1> void update(K key1, V value1, K1 key2, V1 value2) throws SQLException;

    <K, V> void delete(K key, V value) throws SQLException;

    <K, V, T> T findOne(K key, V value) throws SQLException;

    <T> Set<T> findAll() throws SQLException;

}