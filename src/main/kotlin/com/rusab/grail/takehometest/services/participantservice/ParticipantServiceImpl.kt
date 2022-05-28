package com.rusab.grail.takehometest.services.participantservice

import com.rusab.grail.takehometest.GrailTakeHomeTestApplication
import com.rusab.grail.takehometest.exceptions.ParticipantNotFoundException
import com.rusab.grail.takehometest.models.Participant
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ParticipantServiceImpl : ParticipantService {

    val logger = LoggerFactory.getLogger(GrailTakeHomeTestApplication::class.java)
    private var participantList: ArrayList<Participant> = ArrayList()

    override fun enrollParticipant(participant: Participant) {
        participantList.add(participant)
        logger.info("Enrolled Participant with referenceNumber: ${participant.referenceNumber}")
    }

    override fun getParticipantByReferenceNumber(referenceNumber: String): Participant {
        if (participantList.size == 0) throw ParticipantNotFoundException(referenceNumber)

        val filteredParticipant = participantList.filter { it.referenceNumber == referenceNumber }
        if (filteredParticipant.isEmpty()) throw ParticipantNotFoundException(referenceNumber)
        return filteredParticipant[0]
    }
}