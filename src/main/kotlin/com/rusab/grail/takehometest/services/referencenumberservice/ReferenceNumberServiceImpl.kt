package com.rusab.grail.takehometest.services.referencenumberservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ReferenceNumberServiceImpl : ReferenceNumberService {

    val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)
    private val referenceNumberLength = 6

    override fun generateReferenceNumber(): String {
        logger.info("Starting to generate reference Number")
        val firstHalfOfReferenceNumber = List(4) { ('A'..'Z').random() }.joinToString("")
        val secondHalfOfReferenceNumber = List(4) { ('0'..'9').random() }.joinToString("")
        logger.info("Generated reference number: $firstHalfOfReferenceNumber - $secondHalfOfReferenceNumber")
        return "$firstHalfOfReferenceNumber-$secondHalfOfReferenceNumber"
    }
}