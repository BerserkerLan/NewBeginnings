package com.rusab.grail.takehometest.services.validationservice

import com.rusab.grail.takehometest.models.Participant

interface ValidationService {

    /**
     * Determines if the information in a participant is valid to store
     */
    fun validateParticipant(participant: Participant)
}