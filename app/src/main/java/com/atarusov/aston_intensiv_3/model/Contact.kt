package com.atarusov.aston_intensiv_3.model

data class Contact(
    val id: Long = -1,
    val name: String,
    val lastname: String,
    val phoneNumber: String,
    val isSelected: Boolean = false,
)
