package com.example.gotour.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.gotour.R
import com.example.gotour.databinding.FragmentCityInformationBinding

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CityInformation : Fragment() {
    private var _binding: FragmentCityInformationBinding?=null
    private val binding get() = _binding!!
    private lateinit var db:FirebaseFirestore
    private val viewModel:HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= Firebase.firestore
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=DataBindingUtil.inflate(inflater,R.layout.fragment_city_information,container,false)

        val root: View=binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlaces(db)
    }




    companion object {

    }
}