package com.phule.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.phule.mongodb.model.Movie;
import com.phule.mongodb.repository.MovieRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class MongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
    }
    @Bean
    ApplicationRunner demoData(MovieRepository movieRepository){
        return agrs -> {
            movieRepository.deleteAll().thenMany(
                    Flux.just("The Silence of the Lambdas","Back to the Future","AEon Flux","Meet the Fluxers","The Fluxxinator",
                        "Flux Gordon","Y Tu Mono Tambien")
                        .map(Movie::new)
                        .flatMap(movieRepository::save))
                    .thenMany(movieRepository.findAll())
                    .subscribe(System.out::println);

        };
    }
}
