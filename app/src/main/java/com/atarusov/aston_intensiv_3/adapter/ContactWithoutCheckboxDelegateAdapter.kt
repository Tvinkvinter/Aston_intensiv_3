package com.atarusov.aston_intensiv_3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atarusov.aston_intensiv_3.databinding.ContactItemBinding
import com.atarusov.aston_intensiv_3.model.Contact

class ContactWithoutCheckboxDelegateAdapter(
    private val onItemClickListener: (Long) -> Unit,
) : DelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolderWithoutCheckbox(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, contact: Contact) {
        with(holder as ContactViewHolderWithoutCheckbox) {
            binding.tvNameLastname.text = "${contact.name} ${contact.lastname}"
            binding.tvPhoneNumber.text = contact.phoneNumber
            itemView.setOnClickListener {
                onItemClickListener.invoke(contact.id)
            }

            binding.checkbox.visibility = View.GONE
        }
    }

    override fun getItemViewType(contact: Contact): Int {
        return 0
    }

    inner class ContactViewHolderWithoutCheckbox(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}


