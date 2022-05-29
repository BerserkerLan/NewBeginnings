package com.rusab.grail.takehometest.services.referencenumberservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ReferenceNumberServiceImpl : ReferenceNumberService {

    private val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)
    // This should be an even number
    private val referenceNumberLength = 8

    /**
     * This will generate a random reference number of the length [referenceNumberLength]
     * The first half will be a random arrangement of letters from A to Z (All capital letters)
     * The second half will be a random arrangement of numbers from 1 to 9
     * @return Combination of the first half and second half seperated by delimiter
     */
    override fun generateReferenceNumber(): String {
        logger.info("Starting to generate reference Number")
        val firstHalfOfReferenceNumber = List(referenceNumberLength/2) { ('A'..'Z').random() }.joinToString("")
        val secondHalfOfReferenceNumber = List(referenceNumberLength/2) { ('0'..'9').random() }.joinToString("")
        logger.info("Generated reference number: $firstHalfOfReferenceNumber - $secondHalfOfReferenceNumber")
        return "$firstHalfOfReferenceNumber-$secondHalfOfReferenceNumber"
    }
}