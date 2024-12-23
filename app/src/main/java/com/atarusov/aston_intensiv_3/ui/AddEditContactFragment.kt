package com.atarusov.aston_intensiv_3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.atarusov.aston_intensiv_3.databinding.FragmentAddEditContactBinding
import com.atarusov.aston_intensiv_3.model.Contact
import com.atarusov.aston_intensiv_3.viewmodel.ContactsViewModel


class AddEditContactFragment : Fragment() {

    private var _binding: FragmentAddEditContactBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditContactFragmentArgs by navArgs()
    private val viewModel: ContactsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.contactId != -1L) {
            viewModel.contacts.value.find { it.id == args.contactId }
                ?.let { setViewsWithContact(it) }
        }

        binding.btnSave.setOnClickListener {
            val contact = Contact(
                id = args.contactId,
                name = binding.etName.text.toString(),
                lastname = binding.etLastname.text.toString(),
                phoneNumber = binding.etPhoneNumber.text.toString()
            )

            viewModel.saveContact(contact)
            findNavController().navigateUp()
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setViewsWithContact(contact: Contact) {
        binding.etName.setText(contact.name)
        binding.etLastname.setText(contact.lastname)
        binding.etPhoneNumber.setText(contact.phoneNumber)
    }
}