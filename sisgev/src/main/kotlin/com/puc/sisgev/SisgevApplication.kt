package com.puc.sisgev

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SisgevApplication

fun main(args: Array<String>) {
    SpringApplication.run(SisgevApplication::class.java, *args)
}
