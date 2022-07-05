package com.egrf.contactsapp.ui.features.main

import android.os.Bundle
import android.view.*
import com.egrf.contactsapp.R
import com.egrf.contactsapp.databinding.FragmentMainBinding
import com.egrf.contactsapp.ui.di.Injector
import com.egrf.contactsapp.ui.features.base.BaseFragment

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.mainFragmentComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.button.setOnClickListener { viewModel.loadContacts() }
        // TODO: Use the ViewModel
        viewModel.contacts.observe(this.viewLifecycleOwner) { contact ->
            run {
                binding.button.text = contact.size.toString()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}