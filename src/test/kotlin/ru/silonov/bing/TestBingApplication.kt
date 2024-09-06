package ru.silonov.bing

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<BingApplication>().with(TestcontainersConfiguration::class).run(*args)
}
