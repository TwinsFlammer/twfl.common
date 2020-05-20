package br.com.twinsflammer.common.shared.databases.mysql.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public interface ITable {

    void createTable() throws SQLException;

    String getDatabaseName();

    String getTableName();

    <T> void insert(T object);

    <K, V, U, I> void update(HashMap<K, V> keys, U key, I value);

    <K, V> void delete(K key, V value);

    <K, V, T> T findOne(K key, V value);

    <T> Set<T> findAll();

    <K, V, U, I, T> Set<T> findAll(HashMap<K, V> keys);
}
