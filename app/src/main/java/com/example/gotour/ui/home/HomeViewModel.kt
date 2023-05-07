package com.example.gotour.ui.home

import android.media.Rating
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotour.models.HotelsNear
import com.example.gotour.models.Offer
import com.example.gotour.models.Place
import com.example.gotour.ui.home.HomeFragment.Companion.col_hotel
import com.example.gotour.ui.home.HomeFragment.Companion.col_offer
import com.example.gotour.ui.home.HomeFragment.Companion.col_place
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

enum class OfferState { LOADING, SAVED, ERROR, NONE }
enum class HotelState { LOADING, SAVED, ERROR, NONE }

enum class PlaceState { LOADING, SAVED, ERROR, NONE }
class HomeViewModel : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private val _hotels = MutableLiveData<List<HotelsNear>>()
    val hotelList: LiveData<List<HotelsNear>> = _hotels

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    private val _selectOffer = MutableLiveData<Offer>()
    val selectOffer: LiveData<Offer> = _selectOffer

    private val _selectHotel = MutableLiveData<HotelsNear>()
    val selectHotel: LiveData<HotelsNear> = _selectHotel

    private val _selectPlace = MutableLiveData<Place>()
    val selectPlace: LiveData<Place> = _selectPlace

    private val _saveStateOffer = MutableLiveData<OfferState>(OfferState.NONE)
    val savestateOffer: LiveData<OfferState> = _saveStateOffer

    private val _saveStateHotel = MutableLiveData<HotelState>(HotelState.NONE)
    val savestateHotel: LiveData<HotelState> = _saveStateHotel

    private val _saveStatePlace = MutableLiveData<PlaceState>(PlaceState.NONE)
    val savestatePlace: LiveData<PlaceState> = _saveStatePlace


    fun getOffers(db: FirebaseFirestore) {
        loadOffers(db)
    }

    fun getHotel(db: FirebaseFirestore) {
        loadHotel(db)
    }

    fun getPlaces(db: FirebaseFirestore) {
        loadPlaces(db)

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
                Log.e("HomeViewModel", "Error Fetching Offers ${it.message}")
            }.addOnCanceledListener {
                Log.e("HomeViewModel", "Canceled Fetching Offers")
            }.addOnSuccessListener {
                val off = it.toObjects(Offer::class.java)
                _offers.value = off
                Log.d("HomeViewModel", "Offers loaded ${off.size}")
            }


    }

    private fun loadPlaces(db: FirebaseFirestore) {
        _saveStatePlace.value = PlaceState.LOADING
        db.collection(col_place)
            .get().addOnFailureListener {
                Log.e("HomeViewModel", "Error Fetching Places ${it.message}")
            }.addOnCanceledListener {
                Log.e("HomeViewModel", "Canceled Fetching Places")
            }.addOnSuccessListener {
                val plc = it.toObjects(Place::class.java)
                _places.value = plc
                Log.d("HomeViewModel", "Places loaded ${plc.size}")
            }

    }

    fun setOffer(offer: Offer) {
        _selectOffer.value = offer
    }

    fun setHotel(hotel: HotelsNear) {
        _selectHotel.value = hotel
    }

    fun setPlace(place: Place) {
        _selectPlace.value = place
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

    fun deletePlaces(db: FirebaseFirestore) {

        db.collection(col_place).whereEqualTo("name", _selectPlace.value?.name).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    db.collection(col_place).document(it.documents[0].id).delete()
                        .addOnSuccessListener {
                            loadOffers(db)
                        }
                }
            }
    }

        fun updatehotel(
            db: FirebaseFirestore,
            name: String,
            price: String,
            rating: Int
        ) {
            _saveStateHotel.value = HotelState.LOADING
            db.collection(col_hotel).whereEqualTo("name", _selectHotel.value?.name).get()
            db.collection(col_hotel).whereEqualTo("price", _selectHotel.value?.price).get()
            db.collection(col_hotel).whereEqualTo("rating", _selectHotel.value?.rating).get()


                .addOnSuccessListener { query ->
                    if (query.isEmpty) {
                        _saveStateHotel.value = HotelState.ERROR
                    } else {
                        val hotel = query.documents[0].toObject(HotelsNear::class.java)
                        hotel?.let {
                            it.name
                            it.price
                            it.rating
                            db.collection(col_hotel).document(query.documents[0].id).set(it)
                                .addOnSuccessListener {
                                    _saveStateHotel.value = HotelState.ERROR
                                }.addOnFailureListener {
                                    _saveStateHotel.value = HotelState.ERROR
                                }
                        }
                    }
                }.addOnFailureListener {
                    _saveStateHotel.value = HotelState.ERROR
                }


        }

        fun updateoffer(
            db: FirebaseFirestore,
            expiry: String,
            discount: String,
        ) {
            _saveStateOffer.value = OfferState.LOADING
            db.collection(col_offer).whereEqualTo("discount", _selectOffer.value?.discount).get()
                .addOnSuccessListener { query ->
                    if (query.isEmpty) {
                        _saveStateOffer.value = OfferState.ERROR
                    } else {
                        val offer = query.documents[0].toObject(Offer::class.java)
                        offer?.let {
                            it.expiry
                            it.discount

                            db.collection(col_offer).document(query.documents[0].id).set(it)
                                .addOnSuccessListener {
                                    _saveStateOffer.value = OfferState.ERROR
                                }.addOnFailureListener {
                                    _saveStateOffer.value = OfferState.ERROR
                                }
                        }
                    }
                }.addOnFailureListener {
                    _saveStateOffer.value = OfferState.ERROR
                }


        }

        fun updateplace(
            db: FirebaseFirestore,
            name: String,
            place: String,
            image: String
        ) {
            _saveStatePlace.value = PlaceState.LOADING
            db.collection(col_place).whereEqualTo("name", _selectPlace.value?.name).get()
            db.collection(col_place).whereEqualTo("place", _selectPlace.value?.place).get()
            db.collection(col_place).whereEqualTo("image", _selectPlace.value?.image).get()
                .addOnSuccessListener { query ->
                    if (query.isEmpty) {
                        _saveStatePlace.value = PlaceState.ERROR
                    } else {
                        val place = query.documents[0].toObject(Place::class.java)
                        place?.let {
                            it.name
                            it.place
                            it.image

                            db.collection(col_place).document(query.documents[0].id).set(it)
                                .addOnSuccessListener {
                                    _saveStatePlace.value = PlaceState.ERROR
                                }.addOnFailureListener {
                                    _saveStatePlace.value = PlaceState.ERROR
                                }
                        }
                    }
                }.addOnFailureListener {
                    _saveStatePlace.value = PlaceState.ERROR

                }
        }


        fun resetPlaceState() {
            _saveStatePlace.value = PlaceState.NONE
        }


        fun resetOfferState() {
            _saveStateOffer.value = OfferState.NONE
        }

        fun resetHotelState() {
            _saveStateHotel.value = HotelState.NONE
        }
    }












