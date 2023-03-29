package com.example.gotour.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotour.models.HotelsNear
import com.example.gotour.models.Offer
import com.google.firebase.firestore.FirebaseFirestore

enum class OfferState { LOADING, SAVED, ERROR, NONE }
enum class HotelState { LOADING, SAVED, ERROR, NONE }
class HomeViewModel : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private val _hotels = MutableLiveData<List<HotelsNear>>()
    val hotelList: LiveData<List<HotelsNear>> = _hotels

    private val _selectOffer = MutableLiveData<Offer>()
    val selectOffer: LiveData<Offer> = _selectOffer

    private val _selectHotel = MutableLiveData<HotelsNear>()
    val selectHotel: LiveData<HotelsNear> = _selectHotel

    private val _saveStateOffer = MutableLiveData<OfferState>(OfferState.NONE)
    val savestateOffer: LiveData<OfferState> = _saveStateOffer

    private val _saveStateHotel = MutableLiveData<HotelState>(HotelState.NONE)
    val savestateHotel: LiveData<HotelState> = _saveStateHotel


    fun getOffers(db: FirebaseFirestore) {
        loadOffers(db)
    }

    fun getHotel(db: FirebaseFirestore) {
        loadHotel(db)
    }

    private fun loadHotel(db: FirebaseFirestore) {
        _saveStateHotel.value = HotelState.LOADING
        db.collection(col_hotel)
            .get().addOnFailureListener {
                Log.e("HomeViewModel", "Error Fetching Hotels ${it.message}")
            }.addOnCanceledListener {
                Log.e("HomeViewModel", "Canceled Fetching Hotels")
            }.addOnSuccessListener {
                val htl = it.toObjects(HotelsNear::class.java)
                _hotels.value = htl
                Log.d("HomeViewModel", "Hotels loadded ${htl.size}")
            }


    }

    private fun loadOffers(db: FirebaseFirestore) {
        _saveStateOffer.value = OfferState.LOADING
        db.collection(col_offer)
            .get().addOnFailureListener {
                Log.e("HomeViewModel", "Error Feching Offers ${it.message}")
            }.addOnCanceledListener {
                Log.e("HomeViewModel", "Canceled Fetching Offers")
            }.addOnSuccessListener {
                val off = it.toObjects(Offer::class.java)
                _offers.value = off
                Log.d("HomeViewModel", "Offers loadded ${off.size}")
            }


    }

    fun setOffer(offer: Offer) {
        _selectOffer.value = offer
    }

    fun setHotel(hotel: HotelsNear) {
        _selectHotel.value = hotel
    }

    fun deleteOffers(db: FirebaseFirestore) {
        db.collection(col_offer).whereEqualTo("name", _selectOffer.value?.image).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    db.collection(col_offer).document(it.documents[0].id).delete()
                        .addOnSuccessListener {
                            loadOffers(db)
                        }
                }
            }


    }

    fun deleteHotels(db: FirebaseFirestore) {

        db.collection(col_hotel).whereEqualTo("name", _selectHotel.value?.name).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    db.collection(col_hotel).document(it.documents[0].id).delete()
                        .addOnSuccessListener {
                            loadOffers(db)
                        }
                }
            }


    }

    companion object {
        const val col_offer = "offer"
        const val col_hotel = "hotel"
    }
}


