package com.example.gotour.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gotour.R
import com.example.gotour.adapters.HotelsAdapter
import com.example.gotour.adapters.OffersAdapter
import com.example.gotour.adapters.PlaceAdapter
import com.example.gotour.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHotel(db)
        viewModel.getOffers(db)
        viewModel.getPlaces(db)

        viewModel.hotelList.observe(viewLifecycleOwner) {
            Log.d("HomeFragment", "onViewCreated: $it")
            binding.hotelList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.hotelList.adapter = HotelsAdapter { hotel ->
                viewModel.setHotel(hotel)
                findNavController().navigate(R.id.action_nav_home_to_hotelDetailFragment)
            }
            (binding.hotelList.adapter as HotelsAdapter).submitList(it)
        }

        viewModel.offers.observe(viewLifecycleOwner) {
            Log.d("HomeFragment", "onViewCreated: $it")
            binding.offerList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.offerList.adapter = OffersAdapter { offer ->
                viewModel.setOffer(offer)
                findNavController().navigate(R.id.action_nav_home_to_offerDetailFragment)
            }
            (binding.offerList.adapter as OffersAdapter).submitList(it)
        }

        viewModel.places.observe(viewLifecycleOwner) {
            Log.d("HomeFragment", "onViewCreated: $it")
            binding.placeList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.placeList.adapter = PlaceAdapter { place ->
                viewModel.setPlace(place)
                findNavController().navigate(R.id.action_nav_home_to_cityInformation)
            }
            (binding.placeList.adapter as PlaceAdapter).submitList(it)
        }
        viewModel.resetPlaceState()
        binding.placeList.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_cityInformation)
        }
        


        binding.apply {
            hotelList.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_hotelDetailFragment)
            }
        }


        binding.apply {
            offerList.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_offerDetailFragment)
            }
        }


        binding.apply {
            tripImageView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_trip)
            }
        }
        binding.apply {
            hotelImageView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_hotelDetailFragment)
            }
        }
        binding.apply {
            fliteImage.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_flightFragment)

            }

        }
        binding.apply {
            offerImage.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_offerDetailFragment)
            }
        }
        binding.apply {
            tripTextView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_nav_trip)
            }
        }
        binding.apply {
            hotelTextView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_hotelDetailFragment)
            }
        }
        binding.apply {
            flighttextView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_flightFragment)

            }

        }
        binding.apply {
            offerTextView.setOnClickListener {
                findNavController().navigate(R.id.action_nav_home_to_offerDetailFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val col_hotel = "hotel"
        const val col_offer = "offer"
        const val col_place = "place"
    }
}
