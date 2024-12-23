package com.atarusov.aston_intensiv_3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.atarusov.aston_intensiv_3.R
import com.atarusov.aston_intensiv_3.adapter.ContactsAdapter
import com.atarusov.aston_intensiv_3.adapter.ContactsTouchCallback
import com.atarusov.aston_intensiv_3.databinding.FragmentContactsBinding
import com.atarusov.aston_intensiv_3.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext())

        val rvAdapter = ContactsAdapter(
            onItemClickListener = { contactId: Long ->
                if (!viewModel.deleteMode.value) {
                    val action = ContactsFragmentDirections
                        .actionContactsFragmentToAddEditContactFragment(contactId)
                    findNavController().navigate(action)
                }
            },
            onItemSelectListener = { selectedContactId ->
                viewModel.onItemSelect(selectedContactId)
            })
        binding.rvContacts.adapter = rvAdapter

        val itemTouchHelper = ItemTouchHelper(ContactsTouchCallback(viewModel))
        itemTouchHelper.attachToRecyclerView(binding.rvContacts)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteMode.collect { deleteMode ->
                    rvAdapter.deleteMode = deleteMode
                    setDeleteMode(deleteMode)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect { contacts ->
                    rvAdapter.contacts = contacts
                }
            }
        }

        binding.btnActivateDeleteMode.setOnClickListener {
            viewModel.switchDeleteMode(true)
        }
        binding.btnDeactivateDeleteMode.setOnClickListener {
            viewModel.switchDeleteMode(false)
        }
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment_to_addEditContactFragment)
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteSelectedItems()
        }
    }

    private fun setDeleteMode(deleteMode: Boolean) {
        if (deleteMode) {
            binding.btnActivateDeleteMode.visibility = View.GONE
            binding.btnDeactivateDeleteMode.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnAdd.visibility = View.GONE
        } else {
            binding.btnActivateDeleteMode.visibility = View.VISIBLE
            binding.btnDeactivateDeleteMode.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
            binding.btnAdd.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}