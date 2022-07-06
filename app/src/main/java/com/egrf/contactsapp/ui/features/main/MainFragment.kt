package com.egrf.contactsapp.ui.features.main

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.egrf.contactsapp.R
import com.egrf.contactsapp.databinding.FragmentMainBinding
import com.egrf.contactsapp.domain.entity.Contact
import com.egrf.contactsapp.ui.adapters.ContactsAdapter
import com.egrf.contactsapp.ui.di.Injector
import com.egrf.contactsapp.ui.features.base.BaseFragment
import com.egrf.contactsapp.ui.features.details.ContactDetailsFragment.Companion.CONTACT_PARAM
import io.reactivex.disposables.CompositeDisposable

class MainFragment : BaseFragment<MainViewModel>() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ContactsAdapter

    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.mainFragmentComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = ContactsAdapter() { item ->
            onContactItemClicked(item)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView = binding.contacts

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadContacts()
            mDisposable.dispose()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.fetchContactsEvent.observe(this.viewLifecycleOwner) {
            run {
                mDisposable.add(viewModel.fetchContacts().subscribe {
                    adapter.submitData(lifecycle, it)
                })
            }
        }

        viewModel.loadingState.observe(this.viewLifecycleOwner) { isLoading ->
            run {
                binding.loading.isGone = !isLoading
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onContactItemClicked(contact: Contact) {
        findNavController().navigate(
            R.id.navigateToContactDetails,
            bundleOf(CONTACT_PARAM to contact)
        )
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}