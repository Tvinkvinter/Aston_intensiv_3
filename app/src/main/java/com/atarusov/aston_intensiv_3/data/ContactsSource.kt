package com.atarusov.aston_intensiv_3.data

import com.atarusov.aston_intensiv_3.model.Contact

object ContactsSource {
    val contacts = mutableListOf<Contact>()

    init {
        repeat(100) { i ->
            contacts.add(
                Contact(
                    id = i.toLong() + 1,
                    name = "Name ${i + 1}",
                    lastname = "Lastname ${i + 1}",
                    phoneNumber = "+${(1..9999999999999).random()}"
                )
            )
        }
    }
}