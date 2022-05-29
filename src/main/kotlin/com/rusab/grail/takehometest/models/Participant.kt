package com.rusab.grail.takehometest.models

import java.util.*

data class Participant(
    val name: String,
    val dateOfBirth: Date,
    var phoneNumber: String,
    var address: Address,
    var referenceNumber: String?
)
