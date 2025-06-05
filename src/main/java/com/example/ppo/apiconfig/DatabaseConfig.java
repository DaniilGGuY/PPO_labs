package com.example.ppo.apiconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ppo.client.*;
import com.example.ppo.client.jpa.JpaClientRepo;
import com.example.ppo.client.mongo.MongoClientRepo;
import com.example.ppo.consultant.*;
import com.example.ppo.consultant.jpa.JpaConsultantRepo;
import com.example.ppo.consultant.mongo.MongoConsultantRepo;
import com.example.ppo.manager.*;
import com.example.ppo.manager.jpa.JpaManagerRepo;
import com.example.ppo.manager.mongo.MongoManagerRepo;
import com.example.ppo.message.*;
import com.example.ppo.message.jpa.JpaMessageRepo;
import com.example.ppo.message.mongo.MongoMessageRepo;
import com.example.ppo.order.*;
import com.example.ppo.order.jpa.JpaOrderRepo;
import com.example.ppo.order.mongo.MongoOrderRepo;
import com.example.ppo.orderproduct.*;
import com.example.ppo.orderproduct.jpa.JpaOrderProductRepo;
import com.example.ppo.orderproduct.mongo.MongoOrderProductRepo;
import com.example.ppo.product.*;
import com.example.ppo.product.jpa.JpaProductRepo;
import com.example.ppo.product.mongo.MongoProductRepo;
import com.example.ppo.review.*;
import com.example.ppo.review.jpa.JpaReviewRepo;
import com.example.ppo.review.mongo.MongoReviewRepo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
public class DatabaseConfig {
    
    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IClientRepository jpaClientRepository(JpaClientRepo repo) {
        return new JpaClientRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IConsultantRepository jpaConsultantRepository(JpaConsultantRepo repo) {
        return new JpaConsultantRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IManagerRepository jpaManagerRepository(JpaManagerRepo repo) {
        return new JpaManagerRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IMessageRepository jpaMessageRepository(JpaMessageRepo repo) {
        return new JpaMessageRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IOrderRepository jpaOrderRepository(JpaOrderRepo repo) {
        return new JpaOrderRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IOrderProductRepository jpaOrderProductRepository(JpaOrderProductRepo repo) {
        return new JpaOrderProductRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IProductRepository jpaProductRepository(JpaProductRepo repo) {
        return new JpaProductRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "jpa")
    public IReviewRepository jpaReviewRepository(JpaReviewRepo repo) {
        return new JpaReviewRepoImpl(repo);
    }
    

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IClientRepository mongoClientRepository(MongoClientRepo repo) {
        return new MongoClientRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IConsultantRepository mongoConsultantRepository(MongoConsultantRepo repo) {
        return new MongoConsultantRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IManagerRepository mongoManagerRepository(MongoManagerRepo repo) {
        return new MongoManagerRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IMessageRepository mongoMessageRepository(MongoMessageRepo repo) {
        return new MongoMessageRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IOrderRepository mongoOrderRepository(MongoOrderRepo repo) {
        return new MongoOrderRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IOrderProductRepository mongoOrderProductRepository(MongoOrderProductRepo repo) {
        return new MongoOrderProductRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IProductRepository mongoProductRepository(MongoProductRepo repo) {
        return new MongoProductRepoImpl(repo);
    }

    @Bean
    @ConditionalOnProperty(name = "app.database.active", havingValue = "mongo")
    public IReviewRepository mongoReviewRepository(MongoReviewRepo repo) {
        return new MongoReviewRepoImpl(repo);
    }
}
