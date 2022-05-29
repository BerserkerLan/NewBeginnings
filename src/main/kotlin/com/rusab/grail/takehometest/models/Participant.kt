package com.rusab.grail.takehometest.models

import java.time.LocalDate

data class Participant(
    val name: String,
    val dateOfBirth: LocalDate,
    var phoneNumber: String,
    var address: Address,
    var referenceNumber: String?
)
