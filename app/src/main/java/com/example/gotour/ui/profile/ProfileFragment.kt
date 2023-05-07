package com.example.gotour.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gotour.R
import com.example.gotour.databinding.FragmentProfileBinding
import com.example.gotour.ui.home.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: UserViewModel
    private lateinit var auth: FirebaseAuth


    fun saveUser(
        db: FirebaseFirestore,
        name: String,
        phone: String,
    ) {


        if (auth.currentUser != null) {
            val user = hashMapOf(
                "name" to name,
                "phone" to phone

            )
            db.collection("users").document(auth.currentUser!!.uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d("ProfileFragment", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w("ProfileFragment", "Error writing document", e)
                }
        } else {
            Log.d("ProfileFragment", "User is not logged in")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        //

    }
}