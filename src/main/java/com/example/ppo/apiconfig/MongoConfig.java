package com.example.ppo.apiconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.example.ppo.**.mongo",
    includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Mongo.*Repo")
)
public class MongoConfig {}