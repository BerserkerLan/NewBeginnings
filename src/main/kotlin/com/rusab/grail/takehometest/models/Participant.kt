package com.rusab.grail.takehometest.models

import java.util.*

data class Participant(
    val name: String,
    val dateOfBirth: Date,
    val phoneNumber: String,
    val address: Address,
    var referenceNumber: String?
)
