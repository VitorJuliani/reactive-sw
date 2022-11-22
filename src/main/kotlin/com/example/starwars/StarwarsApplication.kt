package com.example.starwars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
class StarwarsApplication

fun main(args: Array<String>) {
    runApplication<StarwarsApplication>(*args)
}
