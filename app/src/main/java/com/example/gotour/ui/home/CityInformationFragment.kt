package com.example.gotour.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gotour.R
import com.example.gotour.adapters.CityAdapter
import com.example.gotour.databinding.FragmentCityInformationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CityInformationFragment : Fragment() {
    private lateinit var _binding: FragmentCityInformationBinding
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_city_information, container, false)

        binding.viewModel =viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCity(db)

        viewModel.city.observe(viewLifecycleOwner) {
            Log.d("CityInformationFragment", "onViewCreated: $it")
            binding.cityList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.cityList.adapter = CityAdapter{ city->
                viewModel.setCity(city)
                findNavController().navigate(R.id.action_cityInformation_to_hotelDetailFragment)

            }
            (binding.cityList.adapter as CityAdapter).submitList(it)

        }







    }


    companion object{
        const val col_city="city"
    }
}
