package com.hiren.practicaltest.ui.fragment

import android.os.Bundle
import android.view.View
import com.hiren.practicaltest.R
import com.hiren.practicaltest.databinding.FragmentInvoiceBinding
import com.hiren.practicaltest.ui.adapter.InvoiceItemAdapter
import com.hiren.practicaltest.utils.BindingAdapters

class InvoiceFragment : BaseFragment<FragmentInvoiceBinding>(R.layout.fragment_invoice) {

    private val _adapter: InvoiceItemAdapter by lazy {
        InvoiceItemAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewAction()
        fetchData()
    }

    private fun setViewAction() {
        binding.recItem.apply {
            setHasFixedSize(true)
            adapter = _adapter
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun fetchData() {
        binding.vmCart = vmCart
        binding.companion = BindingAdapters.Companion
        _adapter.setData(vmCart.cartItemList)
    }

    override fun clearObjects() {
        super.clearObjects()
        vmCart.clearCart()
    }
}