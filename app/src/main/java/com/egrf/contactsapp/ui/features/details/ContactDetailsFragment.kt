package com.egrf.contactsapp.ui.features.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.egrf.contactsapp.databinding.FragmentContactDetailsBinding
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.ui.di.Injector
import com.egrf.contactsapp.ui.extensions.EMPTY
import com.egrf.contactsapp.ui.features.base.BaseFragment
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


class ContactDetailsFragment : BaseFragment<ContactDetailsViewModel>() {

    private var contact: Contact? = null
    private lateinit var binding: FragmentContactDetailsBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.contactDetailsFragmentComponent.inject(this)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        arguments?.let {
            contact = it.getSerializable(CONTACT_PARAM) as? Contact
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        with(binding) {
            name.text = contact?.name
            phone.text = contact?.phone
            phone.setOnClickListener {
                createDialIntentAndStart()
            }
            temperament.text = contact?.temperament.toString()
            val educationPeriodText =
                "${formatDate(contact?.educationPeriod?.start)} - ${formatDate(contact?.educationPeriod?.end)}"
            educationPeriod.text = educationPeriodText
            biography.text = contact?.biography
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()
        val config = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, config)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createDialIntentAndStart() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${preparePhoneNumber(contact?.phone)}")
        startActivity(intent)
    }

    private fun formatDate(date: OffsetDateTime?) =
        date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: String.EMPTY

    private fun preparePhoneNumber(phoneNumber: String?) =
        phoneNumber?.let {
            Regex(PHONE_REGEX).replace(phoneNumber, String.EMPTY)
        } ?: String.EMPTY

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    companion object {
        private const val PHONE_REGEX = "[^0-9]"
        const val CONTACT_PARAM = "CONTACT_PARAM"
    }

}
