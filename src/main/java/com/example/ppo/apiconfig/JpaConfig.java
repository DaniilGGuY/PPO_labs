package com.example.ppo.apiconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.ppo.**.jpa",
    includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*Jpa.*Repo")
)
public class JpaConfig {}
