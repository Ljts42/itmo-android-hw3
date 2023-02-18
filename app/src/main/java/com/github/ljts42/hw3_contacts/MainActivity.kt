package com.github.ljts42.hw3_contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ljts42.hw3_contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsList: List<Contact>
    private var myRequestId: Int = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                myRequestId
            )
        } else {
            initRecyclerView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            myRequestId -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initRecyclerView()
                } else {
                    errorToast()
                }
            }
        }
    }

    private fun initRecyclerView() {
        val viewManager = LinearLayoutManager(this)
        contactsList = fetchAllContacts()
        binding.contactsRecyclerView.apply {
            layoutManager = viewManager
            adapter = ContactAdapter(
                contactsList,
                onClickCall = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${it.phoneNumber}"))
                    startActivity(intent)
                },
                onClickSms = {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${it.phoneNumber}"))
                    startActivity(intent)
                }
            )
        }
        successToast(contactsList.size)
    }

    private fun errorToast() =
        Toast.makeText(
            this,
            getString(R.string.no_permission_msg),
            Toast.LENGTH_SHORT
        ).show()

    private fun successToast(number: Int) =
        Toast.makeText(
            this,
            resources.getQuantityString(R.plurals.contacts_number, number, number),
            Toast.LENGTH_SHORT
        ).show()
}