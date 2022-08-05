package com.hiren.practicaltest.ui.fragment

import android.os.Bundle
import android.view.View
import com.hiren.practicaltest.R
import com.hiren.practicaltest.data.entity.ItemEntity
import com.hiren.practicaltest.data.model.Cart
import com.hiren.practicaltest.databinding.FragmentCartBinding
import com.hiren.practicaltest.ui.activity.MainActivity
import com.hiren.practicaltest.ui.adapter.CartAdapter
import com.hiren.practicaltest.ui.dialog.CheckoutDialog
import com.hiren.practicaltest.utils.BindingAdapters
import com.hiren.practicaltest.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart) {

    private val _adapter: CartAdapter by lazy {
        CartAdapter { item, index, type ->
            if (type == 1) {
                // Increase qty
                vmCart.cartItemList[index].quantity += 1
                _adapter.setData(vmCart.cartItemList)
                calculateCart()

            } else if (type == 2) {
                // Desc qty
                if (item.quantity > 1) {
                    vmCart.cartItemList[index].quantity -= 1
                    _adapter.setData(vmCart.cartItemList)
                    calculateCart()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmCart.obsCartContent.set(BindingAdapters.NO_DATA)
        binding.vmCart = vmCart
        binding.companion = BindingAdapters.Companion

        setViewAction()
        loadCart()
    }

    private fun setViewAction() {
        binding.recItem.apply {
            setHasFixedSize(true)
            adapter = _adapter
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            openCheckOutDialog()
        }
    }

    private fun loadCart() {
        if (vmCart.itemList.isNotEmpty()) {
            generateCart()
        } else {
            vmCart.obsCartContent.set(BindingAdapters.NO_DATA)
        }
    }

    private fun generateCart() {
        val newItemList: MutableList<ItemEntity> = mutableListOf()
        var totalAmount = 0.0
        var totalQty = 0
        for (item in vmCart.itemList) {
            if (item.isSelected) {
                item.quantity = if (item.quantity <= 0) 1 else item.quantity
                totalAmount += (item.amount * item.quantity)
                totalQty += item.quantity
                newItemList.add(item)
            }
        }
        vmCart.obsTotalAmount.set(totalAmount)
        vmCart.obsQuantity.set(totalQty)

        if (newItemList.isNotEmpty()) {
            val cart = Cart(newItemList, 0.0, 0)
            vmCart.cartItem = cart
            vmCart.cartItemList = newItemList
            _adapter.setData(newItemList)
            vmCart.obsCartContent.set(BindingAdapters.CONTENT)
        } else {
            vmCart.obsCartContent.set(BindingAdapters.NO_DATA)
        }
    }

    private fun calculateCart() {
        var totalAmount = 0.0
        var totalQty = 0
        for (item in vmCart.cartItem.cartItem) {
            totalAmount += (item.amount * item.quantity)
            totalQty += item.quantity
        }
        vmCart.obsTotalAmount.set(totalAmount)
        vmCart.obsQuantity.set(totalQty)
    }

    private fun openCheckOutDialog() {
        val checkoutDialog = CheckoutDialog(requireContext(), vmCart) { invoiceNumber ->
            vmCart.invoiceNumber = invoiceNumber
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
            vmCart.invoiceDate = sdf.format(Date())
            (activity as MainActivity).loadFragment(InvoiceFragment(), Constants.FRAGMENT_INVOICE)
        }
        checkoutDialog.showDialog()
    }
}