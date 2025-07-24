package guru.spring_reactive_mongodb.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import static java.util.Collections.singletonList;

//@Configuration
//@EnableMongoAuditing
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    @Value("${sfg.mongohost}")
    String mongoDbHost;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Override
    public String getDatabaseName() {
        return "reactive_mongo";
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.credential(MongoCredential.createCredential("sebastian",
                        "admin", "zirael#43#".toCharArray()))
                .applyToClusterSettings(settings -> {
                    settings.hosts((singletonList(
                            new ServerAddress(mongoDbHost, 27017)
                    )));
                });
    }
}