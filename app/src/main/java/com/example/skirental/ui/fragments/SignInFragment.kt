package com.example.skirental.ui.fragments

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skirental.R
import com.example.skirental.databinding.SignInFragmentBinding
import com.example.skirental.ui.activities.AdminActivity
import com.example.skirental.ui.activities.MainActivity
import com.example.skirental.utils.Constants
import com.example.skirental.utils.Prefs
import com.example.skirental.utils.bindSignInClick
import com.example.skirental.viewmodels.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class SignInFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: SignInFragmentBinding
    private lateinit var prefs: Prefs

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            signInWithGoogle(data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding = SignInFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        prefs = Prefs(requireContext())

        setupObservers()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // it is generated
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        mAuth = FirebaseAuth.getInstance()
        signInAnonymously()

        return binding.root
    }

    private fun signInAnonymously() {
        binding.tvSignInAnonymously.setOnClickListener {
            binding.isLoading = true
            binding.tvSignInAnonymously.isEnabled = false
            mAuth.signInAnonymously()
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        addUserToFirebase(user)
                        binding.isLoading = false
                        binding.tvSignInAnonymously.isEnabled = true
                        if(!prefs.userHasDetails) {
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToLoginDetailsFragment())
                        } else {
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }    }

    private fun setupObservers() {
        viewModel.onSignInClicked.observe(viewLifecycleOwner,
            Observer{
                createIntentSigninGoogle()
            })
    }

    private fun createIntentSigninGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun signInWithGoogle(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val exception = task.exception
        if(task.isSuccessful) {
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Timber.d("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Timber.w("Google sign in failed", e)
            }
        } else {
            Timber.w(exception.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        binding.isLoading = true
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.isLoading = false
                if (task.isSuccessful) {
                    Timber.d( "signInWithCredential:success")
                    val currentUser = mAuth.currentUser
                    addUserToFirebase(currentUser)
                    val isAdmin = mAuth.currentUser?.displayName == Constants.SUPERUSER_NAME
                    if(isAdmin) {
                        val intent = Intent(requireActivity(), AdminActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        if(!prefs.userHasDetails) {
                            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToLoginDetailsFragment())
                        } else {
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                } else {
                    Timber.w( "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun addUserToFirebase(currentUser: FirebaseUser?) {
        val user = hashMapOf(
            "name" to currentUser?.displayName,
            "email" to currentUser?.email,
            "height" to 0,
            "weight" to 0,
            "shoeSize" to 0,
            "experience" to 0,
            "start_date" to "",
            "end_date" to "",
            "additional_comment" to "",
        )
        db.collection("users").document(currentUser?.uid ?: "dump")
            .set(user)
            .addOnSuccessListener { documentReference ->
                Timber.i("User ${currentUser?.displayName} has been added to firestore")
            }
            .addOnFailureListener { e ->
                Timber.w( "Error adding document", e)
            }
    }
}