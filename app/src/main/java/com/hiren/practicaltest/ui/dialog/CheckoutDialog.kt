package com.hiren.practicaltest.ui.dialog

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.hiren.practicaltest.R
import com.hiren.practicaltest.databinding.DialogCheckoutBinding
import com.hiren.practicaltest.utils.BindingAdapters
import com.hiren.practicaltest.viewmodel.CartViewModel

class CheckoutDialog(
    context: Context,
    private val vmCart: CartViewModel,
    private val listener: (String) -> Unit
) {

    private val _dialog: Dialog = Dialog(context)
    private var _binding: DialogCheckoutBinding

    init {
        _dialog.setCancelable(true)
        _dialog.setCanceledOnTouchOutside(true)
        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = LayoutInflater.from(context)
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_checkout, null, false)
        _dialog.setContentView(_binding.root)
        _dialog.window?.setBackgroundDrawableResource(R.color.transparent)

        _binding.imgBack.setOnClickListener {
            closeDialog()
        }
        vmCart.obsTotalPayable.set(vmCart.obsTotalAmount.get())
        vmCart.obsReceivedAmount.set(0.0)
        vmCart.obsChangeAmount.set(0.0)

        _binding.vmCart = vmCart
        _binding.companion = BindingAdapters.Companion

        _binding.radDiscountType.setOnCheckedChangeListener { _, _ ->
            calculateItems()
        }

        _binding.btnGenerateInvoice.setOnClickListener {
            if (calculateItems()) {
                listener.invoke((0 until 15000).random().toString())
                closeDialog()
            }
        }

        _binding.edDiscountAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateItems()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        _binding.edReceivedAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateItems()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    private fun calculateItems(): Boolean {

        var isSuccess: Boolean

        val isFixDiscount = _binding.rabFixed.isChecked
        val totalAmount: Double = vmCart.obsTotalAmount.get()
        val discount: Double =
            BindingAdapters.fractionValueInDouble(_binding.edDiscountAmount.text.toString())
        val received: Double =
            BindingAdapters.fractionValueInDouble(_binding.edReceivedAmount.text.toString())

        var payable: Double = totalAmount

        // Check discount
        if (_binding.edDiscountAmount.text.toString().isNotEmpty()) {
            // calculate Discount amount

            val discountedAmount = if (isFixDiscount) {
                totalAmount - discount
            } else {
                if (!isFixDiscount && discount > 99) {
                    _binding.tiDiscountAmount.error =
                        _binding.tiDiscountAmount.context.resources.getString(R.string.err_msg_discount_not_more)
                    isSuccess = false
                    totalAmount
                } else {
                    _binding.tiDiscountAmount.error = null
                    isSuccess = true

                    totalAmount - (totalAmount * discount) / 100
                }

            }

            payable = discountedAmount

            if (totalAmount > discountedAmount) {
                _binding.tiDiscountAmount.error = null
                isSuccess = true
            } else {
                _binding.tiDiscountAmount.error =
                    _binding.tiDiscountAmount.context.resources.getString(R.string.err_msg_discount)
                isSuccess = false
            }
        } else {
            isSuccess = true
            _binding.tiDiscountAmount.error = null
        }

        // Check Received amount
        if (_binding.edReceivedAmount.text.toString().isNotEmpty()) {
            payable = String.format("%.2f", payable).toDouble()
            if (payable <= received) {
                _binding.tiReceivedAmount.error = null
                isSuccess = true
            } else {
                _binding.tiReceivedAmount.error =
                    _binding.tiReceivedAmount.context.resources.getString(R.string.err_msg_received)
                isSuccess = false
            }
        } else {
            _binding.tiReceivedAmount.error =
                _binding.tiReceivedAmount.context.resources.getString(R.string.err_msg_received_required)
            isSuccess = false
        }

        val changeAmount = if (payable < received) received - payable else 0.0
        val remaining = if (payable < received) 0.0 else payable - received

        vmCart.discountAmount = if (isFixDiscount) String.format("%.2f", discount) else "$discount%"
        vmCart.obsTotalPayable.set(payable)
        vmCart.obsRemainAmount.set(remaining)
        vmCart.obsChangeAmount.set(changeAmount)

        return isSuccess
    }

    fun showDialog() {
        _dialog.show()
    }

    private fun closeDialog() {
        if (_dialog.isShowing) _dialog.dismiss()
    }

}

