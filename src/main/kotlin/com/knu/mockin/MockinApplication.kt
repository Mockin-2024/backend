package com.knu.mockin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockinApplication

fun main(args: Array<String>) {
    runApplication<MockinApplication>(*args)
}
