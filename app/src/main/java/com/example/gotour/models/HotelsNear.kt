package com.example.gotour.models

data class HotelsNear(
    val name: String = "",
    val image: String = "",
    val rating: Float = 0f,
    val price: Int = 0,
    val location: String = "",
    val distance: String = "",
    val address: String = "",
    val type: String = "",
    val rooms: Int = 0,
)
