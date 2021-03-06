package com.github.hardlolmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@SpringBootApplication
public class MainBatch {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MainBatch.class);

        EntityManager manager = applicationContext.getBean(EntityManager.class);
        Query query = manager.createQuery("SELECT p FROM Product p");
        List resultList = query.getResultList();
        System.out.println(resultList);
    }
}
