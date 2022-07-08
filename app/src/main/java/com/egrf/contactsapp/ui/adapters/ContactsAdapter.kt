package com.egrf.contactsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egrf.contactsapp.databinding.ContactItemBinding
import com.egrf.contactsapp.domain.entity.Contact


class ContactsAdapter(
    private val clickListener: (Contact) -> Unit
) : PagingDataAdapter<Contact, ContactsAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        if (contact != null) {
            holder.bind(contact, clickListener)
        }
    }

    class ViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
            with(binding) {
                contactName.text = contact.name
                contactPhone.text = contact.phone
                contactHeight.text = contact.height.toString()
                root.setOnClickListener { clickListener(contact) }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.name.equals(newItem.name)
        }
    }
}
