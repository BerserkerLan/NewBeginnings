package com.rusab.grail.takehometest.services.referencenumberservice

import com.rusab.grail.takehometest.models.Participant

interface ReferenceNumberService {
    fun generateReferenceNumber(): String
}