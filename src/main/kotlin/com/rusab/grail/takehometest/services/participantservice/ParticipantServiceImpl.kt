package com.rusab.grail.takehometest.services.participantservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import com.rusab.grail.takehometest.exceptions.ParticipantNotFoundException
import com.rusab.grail.takehometest.exceptions.ReferenceNumberAlreadyUsedException
import com.rusab.grail.takehometest.models.Participant
import com.rusab.grail.takehometest.models.ParticipantUpdate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParticipantServiceImpl : ParticipantService {

    private val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)
    // Using a map of the referenceNumber of a participant to the actual participant for quicker updates and retrievals
    private var referenceNumberToParticipantMap: HashMap<String, Participant> = HashMap()

    /**
     * Add the participant to the [referenceNumberToParticipantMap] in the form of { referenceNumber : participant }
     * If the referenceNumber is already used, throw [ReferenceNumberAlreadyUsedException]
     * @param participant the Participant to enroll
     * @return referenceNumber the referenceNumber used to save the participant, this can then be used to retrieve, update or remove the participant
     * @throws [ReferenceNumberAlreadyUsedException] if the reference Number is already used
     */
    override fun enrollParticipant(participant: Participant): String {
        // The referenceNumber might be a duplicate (if we were using an external service this would be more useful as we cannot rely on external service to always give unique referenceNumbers)
        if (referenceNumberToParticipantMap.containsKey(participant.referenceNumber!!)) throw ReferenceNumberAlreadyUsedException(participant.referenceNumber!!)
        referenceNumberToParticipantMap[participant.referenceNumber!!] = participant
        logger.info("Enrolled Participant with referenceNumber: ${participant.referenceNumber}")
        return participant.referenceNumber.toString()
    }

    /**
     * Retrieve participant using it's unique referenceNumber, if there are no matches or there are no participants, it will throw [ParticipantNotFoundException]
     * @param referenceNumber the unique referenceNumber of the participant
     * @return [Participant] the participant that was found
     * @throws [ParticipantNotFoundException] if the participant is not found or there are no participants
     */
    override fun getParticipantByReferenceNumber(referenceNumber: String): Participant {
        if (referenceNumberToParticipantMap.size == 0) throw ParticipantNotFoundException(referenceNumber)
        return findParticipantOrThrowException(referenceNumber)
    }

    /**
     * Update the participant using the details in the participantUpdate, all the fields are nullable thus we must check what to actually update
     * @param participantUpdate contains variables to update, variables to not update are null
     * @param referenceNumber the unique referenceNumber of the participant
     */
    override fun updateParticipantDetails(participantUpdate: ParticipantUpdate, referenceNumber: String) {
        val filteredParticipant = findParticipantOrThrowException(referenceNumber)

        if (participantUpdate.phoneNumber != null) filteredParticipant.phoneNumber = participantUpdate.phoneNumber
        if (participantUpdate.address != null) filteredParticipant.address = participantUpdate.address

        referenceNumberToParticipantMap[referenceNumber] = filteredParticipant
        logger.info("Updated Participant details with referenceNumber: $referenceNumber")
    }

    /**
     * Remove the participant using it's referenceNumber, throw a [ParticipantNotFoundException] if participant does not exist
     * @param referenceNumber the unique referenceNumber of the participant
     * @throws [ParticipantNotFoundException] if participant does not exist with referenceNumber
     */
    override fun removeParticipant(referenceNumber: String) {
        // Don't attempt to remove anything if participant doesn't exist
        findParticipantOrThrowException(referenceNumber)

        referenceNumberToParticipantMap.remove(referenceNumber)
        logger.info("Removed participant with referenceNumber: $referenceNumber")
    }

    /**
     * If the participant exists in the [referenceNumberToParticipantMap], we return it, else we throw [ParticipantNotFoundException]
     * @param referenceNumber the unique referenceNumber of the participant
     * @return participant that was found using the referenceNumber
     * @throws [ParticipantNotFoundException] if participant does not exist with referenceNumber
     */
    private fun findParticipantOrThrowException(referenceNumber: String): Participant {
        if (!referenceNumberToParticipantMap.containsKey(referenceNumber)) throw ParticipantNotFoundException(referenceNumber)
        return referenceNumberToParticipantMap[referenceNumber]!!
    }
}