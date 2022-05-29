package com.rusab.grail.takehometest.services.validationservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import com.rusab.grail.takehometest.exceptions.ValidationFailedException
import com.rusab.grail.takehometest.models.Participant
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class ValidationServiceImpl: ValidationService {

    private val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)

    private val mobileNumberPatternUS = Pattern.compile("^(\\([0-9]{3}\\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}\$")
    private val zipCodePattern = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?\$")

    /**
     * Validate participant phone number and Zip Code, and throw [ValidationFailedException] if otherwise
     * @param participant the participant to validate
     * @throws [ValidationFailedException] if any validation fails
     */
    override fun validateParticipant(participant: Participant) {
        logger.info("Starting to validate Participant with referenceNumber: ${participant.referenceNumber}")
        validatePhoneNumber(participant)
        validateZipCode(participant)
        logger.info("Finished validating Participant with referenceNumber: ${participant.referenceNumber}")
    }

    /**
     * Validate that the phone number of the participant matches the US format of phone numbers
     * Throw [ValidationFailedException] if it does not
     * @param participant the Participant whose phone number we need to validate
     * @throws [ValidationFailedException] if the phone number is not valid
     */
    private fun validatePhoneNumber(participant: Participant) {
        val match = mobileNumberPatternUS.matcher(participant.phoneNumber)
        val valid = match.find() && match.group().equals(participant.phoneNumber)
        if (!valid) {
            throw ValidationFailedException(participant.referenceNumber)
        }
    }

    /**
     * Validate that the ZIP code of the participant matches the standard ZIP code format of the US
     * Throw [ValidationFailedException] if it does not
     * @param participant the Participant whose ZIP code we need to validate
     * @throws [ValidationFailedException] if the ZIP code is not valid
     */
    private fun validateZipCode(participant: Participant) {
        val match = zipCodePattern.matcher(participant.address.zipCode)
        val valid = match.find() && match.group().equals(participant.address.zipCode)
        if (!valid) {
            throw ValidationFailedException(participant.referenceNumber)
        }
    }
}