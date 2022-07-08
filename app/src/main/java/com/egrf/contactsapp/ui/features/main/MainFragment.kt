package com.egrf.contactsapp.ui.features.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
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

class MainFragment : BaseFragment<MainViewModel>(), SearchView.OnQueryTextListener {

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
        setHasOptionsMenu(true)
        adapter = ContactsAdapter() { item ->
            onContactItemClicked(item)
        }
        binding.toolbar.title = "Contact App"
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
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

        viewModel.fetchContactsFromDb.observe(this.viewLifecycleOwner) { loadFromDb ->
            run {
                if (loadFromDb) {
                    submitAllDataFromDb()
                } else {
                    mDisposable.dispose()
                }
            }
        }

        viewModel.clearContactListEvent.observe(this.viewLifecycleOwner) {
            adapter.submitData(lifecycle, PagingData.empty())
        }

        viewModel.loadingState.observe(this.viewLifecycleOwner) { isLoading ->
            run {
                binding.loading.isGone = !isLoading
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search_button)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(searchText: String?): Boolean {
        mDisposable.dispose()
        if (!searchText.isNullOrBlank() && searchText.length >= 2) {
            searchData(searchText)
        } else {
            submitAllDataFromDb()
        }
        return true
    }

    private fun submitAllDataFromDb() {
        mDisposable.add(viewModel.fetchAllContacts().subscribe {
            adapter.submitData(requireActivity().lifecycle, it)
        })
    }

    private fun searchData(searchText: String) {
        mDisposable.add(viewModel.searchContacts(searchText).subscribe {
            adapter.submitData(requireActivity().lifecycle, it)
        })
    }

    private fun onContactItemClicked(contact: Contact) {
        findNavController().navigate(
            R.id.navigateToContactDetails,
            bundleOf(CONTACT_PARAM to contact)
        )
    }

    override fun onDestroyView() {
        mDisposable.dispose()
        super.onDestroyView()
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}