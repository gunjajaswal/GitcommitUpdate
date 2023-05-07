package com.example.gotour.models

data class HotelsNear(
    val name: String = "",
    val image: String = "https://res.cloudinary.com/simplotel/image/upload/x_0,y_235,w_4512,h_2538,c_crop,q_80,fl_progressive/w_600,h_337,f_auto,c_fit/levana-suites-madan-mohan-malviya-marg/Facade_3_h3jfc6",
    val rating:Float= 0f,
    val price: Int = 0,
    val location: String = "",
    val distance: String = "",
    val address: String = "",
    val type: String = "",
    val rooms: Int = 0,
)
