package br.com.twinsflammer.common.shared.databases.mongo.dao;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.databases.mongo.data.Mongo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public abstract class IDocument {
    private final String collectionName, database;

    public <T> void insert(T document) {
        // TODO auto-generated method
    }

    public <T> void delete(T document) {
        // TODO auto-generated method
    }

    public <T> T update(Document document) {
        return null;
    }

    public <T> T findOne(T document) {
        return null;
    }

    public <T> Set<T> findAll(T document) {
        return null;
    }

    public Mongo getMongo() {
        return Common.getInstance().getDatabaseManager().getMongoManager().getDatabase(this.database);
    }
}