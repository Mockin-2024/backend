package com.knu.mockin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class MockinApplication

fun main(args: Array<String>) {
    runApplication<MockinApplication>(*args)
}
