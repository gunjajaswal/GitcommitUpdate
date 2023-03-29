package com.example.gotour.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gotour.MainActivity
import com.example.gotour.R
import com.example.gotour.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    //  add binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
        }

        binding.apply {
            signInbtn.setOnClickListener { loginUser() }

        }
        binding.apply {
            phoneLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_phoneLoginFragment)

            }
        }
        binding.apply {
            googleLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_googleLoginFragment)
            }
        }

    }


    private fun loginUser() {
        disableButton()
        binding.apply {
            val email = editloginEmail.text.toString()
            val password = editloginpassword.text.toString()

            if (email.isEmpty()) {
                binding.editloginEmail.setError("Please enter password")
                return
            }
            if (password.isEmpty()) {
                binding.editloginpassword.setError("Please enter password")
                return
            }
            auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
                updateUI(it.message, null)
            }.addOnSuccessListener {
                updateUI("success", it.user)
            }

        }
    }

    private fun updateUI(message: String?, user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        } else {
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
            enableButtons()
        }

    }

    private fun enableButtons() {
        binding.signInbtn.isEnabled = true
    }

    private fun disableButton() {
        binding.signInbtn.isEnabled = false
    }
}



