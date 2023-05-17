package com.example.gotour.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotour.models.CityInformation
import com.example.gotour.ui.home.CityInformationFragment.Companion.col_city
import com.google.firebase.firestore.FirebaseFirestore
enum class CityState{
    LOADING,
    ERROR,
    NONE,SAVED
}

class CityViewModel: ViewModel(){
    private val _city= MutableLiveData<List<CityInformation>>()
    val city:LiveData<List<CityInformation>> = _city

    private val _selectedCity= MutableLiveData<CityInformation>()
    val selectedCity:LiveData<CityInformation> = _selectedCity

    private val _savesStateCity= MutableLiveData<CityState>(CityState.NONE)
    val savesStateCity:LiveData<CityState> = _savesStateCity

    fun getCity(db: FirebaseFirestore){
        loadCity(db)
    }

    private fun loadCity(db: FirebaseFirestore) {
       _savesStateCity.value=CityState.LOADING
        db.collection(col_city).get().addOnFailureListener {
            Log.e("CityViewModel", "Error Fetching city ${it.message}")
        }.addOnCanceledListener {
            Log.e("CityViewModel", "Fetching city canceled")
        }.addOnSuccessListener {
            val cities=it.toObjects(CityInformation::class.java)
            _city.value=cities
            Log.d("CityViewModel", "Cities Loaded ${cities.size}")


        }
    }
    fun setCity(city: CityInformation) {
        _selectedCity.value=city
    }

    fun deleteCity(db: FirebaseFirestore) {
        db.collection(col_city).whereEqualTo("name", _selectedCity.value?.image).get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    db.collection(col_city).document(it.documents[0].id).delete()
                        .addOnSuccessListener {
                            loadCity(db)
                        }
                }
            }


    }

    fun updateCity(db: FirebaseFirestore,
       image:String,
    name:String,
    rating:Float,
    meters:Float){
        _savesStateCity.value=CityState.LOADING
        db.collection(col_city).whereEqualTo("name",_selectedCity.value?.name).get()
        db.collection(col_city).whereEqualTo("image",_selectedCity.value?.image).get()
        db.collection(col_city).whereEqualTo("rating",_selectedCity.value?.rating).get()
        db.collection(col_city).whereEqualTo("meters",_selectedCity.value?.meters).get()
            .addOnSuccessListener {query ->
                if (query.isEmpty){
                    _savesStateCity.value=CityState.ERROR
                    } else{
                    val city=query.documents[0].toObject(CityInformation::class.java)
                    city?.let {
                        it.image
                        it.name

                        it.rating

                        it.meters
                        db.collection(col_city).document(query.documents[0].id).set(it)
                            .addOnSuccessListener {
                                _savesStateCity.value=CityState.ERROR

                            }.addOnFailureListener {
                                _savesStateCity.value=CityState.ERROR
                            }
                    }
                }

            }.addOnFailureListener(){
                _savesStateCity.value=CityState.ERROR
            }
    }
    fun resetCityState(){
        _savesStateCity.value=CityState.NONE
    }




}