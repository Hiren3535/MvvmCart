package com.hiren.practicaltest.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.hiren.practicaltest.utils.BindingAdapters

class CartViewModel(application: Application) : AndroidViewModel(application) {

    // UI Progress for Item View
    val obsItemContent: ObservableField<String> = ObservableField(BindingAdapters.PROGRESS)

    // UI Progress for Cart list View
    val obsCartContent: ObservableField<String> = ObservableField(BindingAdapters.PROGRESS)
}