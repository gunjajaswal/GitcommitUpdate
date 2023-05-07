package com.example.gotour

import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import coil.load
import com.example.gotour.ui.home.OfferState


@BindingAdapter("load_image")
fun ImageView.setImage(url: String?) {
    load(url)
}


@BindingAdapter("set_visibility")
fun ProgressBar.setVisibility(isVisible: Boolean) {

    visibility = if (isVisible) {
        ProgressBar.VISIBLE
    } else {
        ProgressBar.GONE
    }
}

@BindingAdapter("set_save_state")
fun ProgressBar.setVisibility(state: OfferState) {
    visibility = if (state == OfferState.LOADING) {
        ProgressBar.VISIBLE
    } else {
        ProgressBar.GONE
    }
}

