package com.egrf.contactsapp.ui.adapters

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.egrf.contactsapp.domain.entity.Contact

class ContactDiffCallback(
    private var oldContactList: List<Contact> = emptyList(),
    private var newContactList: List<Contact> = emptyList()
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldContactList.size
    }

    override fun getNewListSize(): Int {
        return newContactList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContactList[oldItemPosition].id === newContactList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee: Contact = oldContactList[oldItemPosition]
        val newEmployee: Contact = newContactList[newItemPosition]
        return oldEmployee.name.equals(newEmployee.name)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
