package com.hiren.practicaltest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hiren.practicaltest.R
import com.hiren.practicaltest.databinding.ActivityMainBinding
import com.hiren.practicaltest.ui.adapter.ItemAdapter
import com.hiren.practicaltest.viewmodel.CartViewModel

class MainActivity : AppCompatActivity() {

    private val _viewModel: CartViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(CartViewModel::class.java)
    }

    private val _adapter: ItemAdapter by lazy {
        ItemAdapter { _, _ -> }
    }

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        setViewActions()
        fetchItems()
    }

    private fun setViewActions() {
        _binding.recItem.adapter = _adapter

    }

    private fun fetchItems() {
    }

}