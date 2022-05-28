package com.rusab.grail.takehometest.services.validationservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import com.rusab.grail.takehometest.exceptions.ValidationFailedException
import com.rusab.grail.takehometest.models.Participant
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class ValidationServiceImpl: ValidationService {

    val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)

    val mobileNumberPatternUS = Pattern.compile("^(\\([0-9]{3}\\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}\$")
    val zipCodePattern = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?\$")

    override fun validateParticipant(participant: Participant) {
        logger.info("Starting to validate Participant with referenceNumber: ${participant.referenceNumber}")
        validatePhoneNumber(participant)
        validateZipCode(participant)
        logger.info("Finished validating Participant with referenceNumber: ${participant.referenceNumber}")
    }

    private fun validatePhoneNumber(participant: Participant) {
        val match = mobileNumberPatternUS.matcher(participant.phoneNumber)
        val valid = match.find() && match.group().equals(participant.phoneNumber)
        if (!valid) {
            throw ValidationFailedException(participant.referenceNumber)
        }
    }

    private fun validateZipCode(participant: Participant) {
        val match = zipCodePattern.matcher(participant.address.zipCode)
        val valid = match.find() && match.group().equals(participant.address.zipCode)
        if (!valid) {
            throw ValidationFailedException(participant.referenceNumber)
        }
    }
}