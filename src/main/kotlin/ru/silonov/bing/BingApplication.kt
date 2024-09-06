package ru.silonov.bing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BingApplication

fun main(args: Array<String>) {
    runApplication<BingApplication>(*args)
}
