package com.sotatek.widget.data.remote.dto

import java.io.Serializable

data class CoinDto(
    val coinName: String,
    val price: String,
    val vol: String,
    val priceTotal: String,
    val percent: String
) : Serializable