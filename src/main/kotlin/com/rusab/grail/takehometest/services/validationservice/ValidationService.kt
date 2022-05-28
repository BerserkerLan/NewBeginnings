package com.rusab.grail.takehometest.services.validationservice

import com.rusab.grail.takehometest.models.Participant

interface ValidationService {

    fun validateParticipant(participant: Participant)
}