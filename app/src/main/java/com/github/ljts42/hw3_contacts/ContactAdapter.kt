package com.github.ljts42.hw3_contacts

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contacts: List<Contact>,
    private val onClickCall: (Contact) -> Unit,
    private val onClickSms: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val nameView = root.findViewById<TextView>(R.id.contact_name)
        private val phoneNumberView = root.findViewById<TextView>(R.id.contact_phone)
        private val contactAvatarView = root.findViewById<ImageView>(R.id.contact_avatar)
        val buttonCallView: ImageView = root.findViewById(R.id.button_call)
        val buttonSmsView: ImageView = root.findViewById(R.id.button_sms)

        fun bind(contact: Contact) {
            nameView.text = contact.name
            phoneNumberView.text = contact.phoneNumber
            if (contact.avatarUri != "default") {
                contactAvatarView.setImageURI(Uri.parse(contact.avatarUri))
            } else {
                contactAvatarView.setImageResource(R.drawable.ic_default_avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
        holder.buttonCallView.setOnClickListener {
            onClickCall(contacts[holder.adapterPosition])
        }
        holder.buttonSmsView.setOnClickListener {
            onClickSms(contacts[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(contacts[position])

    override fun getItemCount() = contacts.size
}
