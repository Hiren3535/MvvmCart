package com.hiren.practicaltest.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hiren.practicaltest.R
import com.hiren.practicaltest.data.entity.ItemEntity
import com.hiren.practicaltest.databinding.ActivityMainBinding
import com.hiren.practicaltest.ui.adapter.ItemAdapter
import com.hiren.practicaltest.ui.fragment.CartFragment
import com.hiren.practicaltest.utils.BindingAdapters
import com.hiren.practicaltest.utils.Constants
import com.hiren.practicaltest.viewmodel.CartViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    private val _viewModel: CartViewModel by lazy {
        ViewModelProvider(this@MainActivity)[CartViewModel::class.java]
    }

    private val _adapter: ItemAdapter by lazy {
        ItemAdapter { _, index, type ->
            if (type == 1) {
                // Add item to cart
                _viewModel.itemList[index].isSelected = true
                _adapter.setData(_viewModel.itemList)
                _viewModel.obsBadge.set(_viewModel.obsBadge.get() + 1)
            } else if (type == 2) {
                // Delete item from count
                _viewModel.itemList[index].isSelected = false
                _adapter.setData(_viewModel.itemList)
                _viewModel.obsBadge.set(_viewModel.obsBadge.get() - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        _viewModel.obsItemContent.set(BindingAdapters.PROGRESS)
        _binding.vmCart = _viewModel
        _binding.companion = BindingAdapters.Companion
        setViewActions()
        fetchItems()

        _viewModel.obsRefreshItem.addOnPropertyChangedCallback(object :
            OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (_viewModel.obsRefreshItem.get()) {
                    fetchItems()
                    _viewModel.obsRefreshItem.set(false)
                }
            }
        })
    }

    private fun setViewActions() {
        _binding.recItem.setHasFixedSize(true)
        _binding.recItem.adapter = _adapter

        _binding.imgCart.setOnClickListener {
            loadFragment(CartFragment(), Constants.FRAGMENT_CART)
        }
    }

    fun loadFragment(fragment: Fragment?, tag: String?) {
        try {
            val fragmentPopped = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (tag.equals(Constants.FRAGMENT_INVOICE)) {
                removeCartFragment()
            }
            if (!fragmentPopped && fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun removeCartFragment() {
        try {
            val cartFragment =
                supportFragmentManager.findFragmentByTag(Constants.FRAGMENT_CART) as CartFragment?
            if (cartFragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .remove(cartFragment)
                    .commit()
                super.onBackPressed()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun fetchItems() {
        if (_viewModel.itemList.isNotEmpty()) {
            _adapter.setData(_viewModel.itemList)
            _viewModel.obsItemContent.set(BindingAdapters.CONTENT)
        } else
            _viewModel.getAllItems(application).observe(this) {
                if (it.isNotEmpty()) {
                    val list: MutableList<ItemEntity> = it.toMutableList()
                    _adapter.setData(list)
                    _viewModel.itemList = list
                    _viewModel.obsItemContent.set(BindingAdapters.CONTENT)
                } else {
                    val list: MutableList<ItemEntity> = mutableListOf()
                    _adapter.setData(list)
                    _viewModel.itemList = list
                    _viewModel.obsItemContent.set(BindingAdapters.NO_DATA)
                }
            }
    }

}