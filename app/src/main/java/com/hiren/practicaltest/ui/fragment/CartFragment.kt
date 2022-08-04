package com.hiren.practicaltest.ui.fragment

import android.os.Bundle
import android.view.View
import com.hiren.practicaltest.R
import com.hiren.practicaltest.databinding.FragmentCartBinding
import com.hiren.practicaltest.utils.BindingAdapters

class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vmCart = vmCart
        binding.companion = BindingAdapters.Companion
    }

}