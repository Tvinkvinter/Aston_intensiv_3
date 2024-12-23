package com.atarusov.aston_intensiv_3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atarusov.aston_intensiv_3.databinding.ContactItemBinding
import com.atarusov.aston_intensiv_3.model.Contact

class ContactsAdapter(
    private val onItemClickListener: (Long) -> Unit,
    private val onItemSelectListener: (Long) -> Unit,
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    var deleteMode = false
        set(value) {
            if (field != value) {
                field = value
                notifyItemRangeChanged(0, contacts.size)
            }
        }

    var contacts = listOf<Contact>()
        set(value) {
            val diffUtil = ContactsDiffUtil(field, value)
            val diffResult = DiffUtil.calculateDiff(diffUtil)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    override fun getItemCount(): Int = contacts.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        with(holder) {
            val contact = contacts[position]

            binding.tvNameLastname.text = "${contact.name} ${contact.lastname}"
            binding.tvPhoneNumber.text = contact.phoneNumber
            itemView.setOnClickListener {
                onItemClickListener.invoke(contact.id)
            }

            binding.checkbox.visibility = if (deleteMode) View.VISIBLE else View.GONE
            binding.checkbox.isChecked = contact.isSelected

            binding.checkbox.setOnClickListener {
                onItemSelectListener.invoke(contact.id)
            }
        }
    }

    inner class ContactsViewHolder(val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}