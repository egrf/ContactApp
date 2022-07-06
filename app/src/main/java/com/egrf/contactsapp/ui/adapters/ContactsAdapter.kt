package com.egrf.contactsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.egrf.contactsapp.databinding.ContactItemBinding
import com.egrf.contactsapp.domain.entity.Contact


class ContactsAdapter(
    private val clickListener: (Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = list[position]
        holder.bind(contact, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateContactList(list: List<Contact>) {
        val diffCallback = ContactDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
            binding.contactName.text = contact.name
            binding.contactPhone.text = contact.phone
            binding.contactHeight.text = contact.height.toString()
            binding.root.setOnClickListener { clickListener(contact) }
        }
    }
}
