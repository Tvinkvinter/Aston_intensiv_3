package com.atarusov.aston_intensiv_3.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atarusov.aston_intensiv_3.model.Contact
import com.atarusov.aston_intensiv_3.utils.ContactsDiffUtil

class ContactsAdapter(
    onItemClickListener: (Long) -> Unit,
    onItemSelectListener: (Long) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    private val delegateAdapters: List<DelegateAdapter> = listOf(
        ContactWithoutCheckboxDelegateAdapter(onItemClickListener),
        ContactWithCheckboxDelegateAdapter(onItemSelectListener),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters[getItemViewType(position)].onBindViewHolder(holder, contacts[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (deleteMode) 1 else 0
    }

    override fun getItemCount(): Int = contacts.size

}