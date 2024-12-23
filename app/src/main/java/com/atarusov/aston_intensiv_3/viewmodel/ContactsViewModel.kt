package com.atarusov.aston_intensiv_3.viewmodel

import androidx.lifecycle.ViewModel
import com.atarusov.aston_intensiv_3.data.ContactsSource
import com.atarusov.aston_intensiv_3.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactsViewModel : ViewModel() {

    private val _contacts = MutableStateFlow(ContactsSource.contacts)
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _deleteMode = MutableStateFlow(false)
    val deleteMode: StateFlow<Boolean> = _deleteMode

    fun saveContact(contact: Contact) {
        if (contact.id == -1L || (_contacts.value.find { it.id == contact.id } == null)) {
            val newContact = contact.copy(id = _contacts.value.last().id + 1L)
            addNewContact(newContact)
        } else if (_contacts.value.find { it.id == contact.id } != null) {
            editContact(contact)
        }
    }

    fun switchDeleteMode(deleteMode: Boolean) {
        _deleteMode.value = deleteMode
        if (!deleteMode) clearSelection()
    }

    fun onItemSelect(contactId: Long) {
        _contacts.value = _contacts.value.map { contact ->
            if (contact.id == contactId) contact.copy(isSelected = !contact.isSelected)
            else contact
        }.toMutableList()
    }

    fun deleteSelectedItems() {
        _contacts.value = _contacts.value.toMutableList().apply {
            removeAll { it.isSelected }
        }
        switchDeleteMode(false)
    }

    private fun addNewContact(contact: Contact) {
        _contacts.value = _contacts.value.apply { add(contact) }
    }

    private fun editContact(contact: Contact) {
        _contacts.value = _contacts.value.toMutableList().apply {
            val editIndex = indexOfFirst { it.id == contact.id }
            if (editIndex == -1)
                throw NoSuchElementException("Contact with id ${contact.id} not found")
            this[editIndex] = contact
        }
    }

    private fun clearSelection() {
        _contacts.value = _contacts.value.map {
            if (it.isSelected) it.copy(isSelected = false)
            else it
        }.toMutableList()
    }
}