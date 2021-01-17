package com.github.hardlolmaster.module2.homework4;

import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class MongoStart {
    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private static MongodExecutable mongodExecutable;

    @AfterClass
    public static void afterClass() throws Exception {
        mongodExecutable.stop();
    }

    @Before
    public void setUp() throws Exception {
        String ip = "localhost";
        int port = 27017;

        MongodConfig mongodConfig = MongodConfig.builder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }
}
