package com.redecommunity.common.shared.databases.mongo.data;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collections;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Mongo {
    private final String host, user, password, database;

    @Getter
    private MongoDatabase mongoDatabase;

    public void start() throws MongoException {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoCredential mongoCredential = MongoCredential.createCredential(
                this.user,
                "admin",
                this.password.toCharArray()
        );

        MongoClient mongoClient = new MongoClient(
                new ServerAddress(
                        this.host,
                        27017
                ),
                Collections.singletonList(mongoCredential),
                MongoClientOptions.builder().codecRegistry(
                        pojoCodecRegistry
                ).build()
        );

        this.mongoDatabase = mongoClient.getDatabase(this.database);
    }

    public void refresh() throws MongoException {
        if (this.mongoDatabase == null) this.start();
    }
}
