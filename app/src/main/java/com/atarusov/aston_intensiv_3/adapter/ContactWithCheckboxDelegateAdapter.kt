package com.atarusov.aston_intensiv_3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atarusov.aston_intensiv_3.databinding.ContactItemBinding
import com.atarusov.aston_intensiv_3.model.Contact

class ContactWithCheckboxDelegateAdapter(
    private val onItemSelectListener: (Long) -> Unit,
) : DelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolderWithCheckbox(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, contact: Contact) {
        with(holder as ContactViewHolderWithCheckbox) {
            binding.tvNameLastname.text = "${contact.name} ${contact.lastname}"
            binding.tvPhoneNumber.text = contact.phoneNumber

            binding.checkbox.visibility = View.VISIBLE
            binding.checkbox.isChecked = contact.isSelected

            binding.checkbox.setOnClickListener {
                onItemSelectListener.invoke(contact.id)
            }
        }
    }

    override fun getItemViewType(contact: Contact): Int {
        return 1
    }

    inner class ContactViewHolderWithCheckbox(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

