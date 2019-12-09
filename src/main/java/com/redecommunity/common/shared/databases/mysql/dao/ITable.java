package com.redecommunity.common.shared.databases.mysql.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public interface ITable {

    void createTable() throws SQLException;

    String getTableName() throws SQLException;

    <T> void insert(T object) throws SQLException;

    <K, V, U, I> void update(HashMap<K, V> keys, U key, I value) throws SQLException;

    <K, V> void delete(K key, V value) throws SQLException;

    <K, V, T> T findOne(K key, V value) throws SQLException;

    <T> Set<T> findAll() throws SQLException;
}
