package com.bd.kafkalistener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaResenderApplication

fun main(args: Array<String>) {
    runApplication<KafkaResenderApplication>(*args)
}
