package com.example.gotour.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotour.models.Place
import com.google.firebase.firestore.FirebaseFirestore

enum class cityState { LOADING, SAVED, ERROR, NONE }

class CityViewModel: ViewModel() {
    private val _city= MutableLiveData<List<Place>>()
    val city: LiveData<List<Place>> = _city

    private val _selectCity= MutableLiveData<Place>()
    val selectCity: LiveData<Place> = _selectCity

    private val _saveStateCity= MutableLiveData<cityState>(cityState.NONE)
    val savestateCity: LiveData<cityState> = _saveStateCity



    fun getCity(db: FirebaseFirestore) {

    }

}