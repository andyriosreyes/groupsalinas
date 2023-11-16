package com.andy.rios.elektra.ui.views.home

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andy.rios.elektra.R
import com.andy.rios.elektra.base.BaseFragment
import com.andy.rios.elektra.databinding.FragmentContactHomeBinding
import com.andy.rios.elektra.ui.model.Contact
import com.andy.rios.elektra.ui.model.toPresentation
import com.andy.rios.elektra.ui.util.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactHomeFragment : BaseFragment() {
    lateinit var binding: FragmentContactHomeBinding
    private val viewModel: ContactHomeVM by viewModels()
    private val contactAdapter by lazy { ContactHomeAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.dismissProgress()
        setupViews()
        viewModel.getContactData()
        observeViewModel()
        clickListener()
    }

    private fun clickListener() {
        binding.fab.setOnClickListener {
            val contact = Contact()
            val action = ContactHomeFragmentDirections.actionContactHomeFragmentToContactRegisterFragment(contact)
            findNavController().navigate(action)
            findNavController().clearBackStack(R.id.contactHomeFragment)
        }
    }

    private fun setupViews() {
        setupRecyclerView()
        contactAdapter.onItemClick = {
            val action = ContactHomeFragmentDirections.actionContactHomeFragmentToContactRegisterFragment(it)
            findNavController().navigate(action)
            findNavController().clearBackStack(R.id.contactHomeFragment)
        }
        contactAdapter.onItemDelete = {
            viewModel.getDeleteContact(it.toPresentation())
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = contactAdapter
        }
    }

    private fun visibilityEmpty(state : Boolean){
        if(state){
            binding.tvContactEmpty.text = getString(R.string.empty_contact)
            binding.tvContactEmpty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }else{
            binding.tvContactEmpty.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun observeViewModel() = with(viewModel) {
        stateContact.observe(viewLifecycleOwner){
            when (it) {
                is State.Loading -> {
                    //baseActivity.showProgress()
                }
                is State.NoLoading -> {
                    baseActivity.dismissProgress()
                }
                is State.Empty -> {
                    baseActivity.dismissProgress()
                    visibilityEmpty(true)
                    contactAdapter.submitList(null)
                }
                is State.Failed -> {
                    baseActivity.dismissProgress()
                    visibilityEmpty(true)
                    contactAdapter.submitList(null)
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    baseActivity.dismissProgress()
                    val list = it.responseTo() as ArrayList<Contact>
                    contactAdapter.submitList(list)
                }

                else -> {

                }
            }
        }
        stateDeleteContact.observe(viewLifecycleOwner){
            when (it) {
                is State.Loading -> {
                    baseActivity.dismissProgress()
                }
                is State.NoLoading -> {
                    baseActivity.dismissProgress()
                }
                is State.Empty -> {
                    baseActivity.dismissProgress()
                    visibilityEmpty(true)
                    contactAdapter.submitList(null)
                }
                is State.Failed -> {
                    baseActivity.dismissProgress()
                    visibilityEmpty(true)
                    contactAdapter.submitList(null)
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    baseActivity.dismissProgress()
                    visibilityEmpty(false)
                    val list = it.responseTo() as ArrayList<Contact>
                    contactAdapter.submitList(list)
                }
                else -> {
                }
            }
        }

    }
}