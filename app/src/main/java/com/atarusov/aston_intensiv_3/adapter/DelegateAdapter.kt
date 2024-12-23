package com.atarusov.aston_intensiv_3.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atarusov.aston_intensiv_3.model.Contact

interface DelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, contact: Contact)
    fun getItemViewType(contact: Contact): Int
}