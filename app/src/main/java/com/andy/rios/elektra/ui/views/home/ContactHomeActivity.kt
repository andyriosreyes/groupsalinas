package com.andy.rios.elektra.ui.views.home

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.andy.rios.elektra.R
import com.andy.rios.elektra.base.BaseActivity
import com.andy.rios.elektra.databinding.ActivityContactHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactHomeActivity : BaseActivity() {
    private lateinit var binding : ActivityContactHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewClickListener()
    }

    private fun viewClickListener(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.topAppBar.setNavigationOnClickListener {
            findNavController(R.id.container).navigate(R.id.contactHomeFragment)
        }
    }


}