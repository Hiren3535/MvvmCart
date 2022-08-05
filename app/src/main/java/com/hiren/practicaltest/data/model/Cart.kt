package com.hiren.practicaltest.data.model

import com.hiren.practicaltest.data.entity.ItemEntity

data class Cart(
    var totalAmount: Double = 0.0,
    var receivedAmount: Double = 0.0,
    var changeAmount: Double = 0.0,
    var discountAmount: Double = 0.0,
    var discountType: String = "",
    var totalQuantity: Int = 0,
    var invoiceNumber: String = "",
    var createdDate: String = "",
    var cartItem: MutableList<ItemEntity>
) {
    constructor() : this(0.0, 0.0, 0.0, 0.0, "", 0, "", "", mutableListOf())
    constructor(
        cartItem: MutableList<ItemEntity>,
        totalAmount: Double,
        totalQuantity: Int
    ) : this(totalAmount, 0.0, 0.0, 0.0, "", totalQuantity, "", "", cartItem)
}
