package com.rusab.grail.takehometest

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrailTakeHomeTestApplication

val Logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)

fun main(args: Array<String>) {
	runApplication<GrailTakeHomeTestApplication>(*args)
}
