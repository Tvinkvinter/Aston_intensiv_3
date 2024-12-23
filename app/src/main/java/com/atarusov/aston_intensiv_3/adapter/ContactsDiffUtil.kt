package com.atarusov.aston_intensiv_3.adapter

import androidx.recyclerview.widget.DiffUtil
import com.atarusov.aston_intensiv_3.model.Contact

class ContactsDiffUtil(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}